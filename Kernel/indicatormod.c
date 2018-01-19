#include <linux/init.h>
#include <linux/module.h> /** needed by all modules **/
#include <linux/kernel.h>  /** This is for KERN_ALERT **/
/** To send a signal **/
#include <linux/sched.h>
#include <asm/siginfo.h>
#include <linux/pid_namespace.h>
#include <linux/pid.h>
/** for copy_to_user **/
#include <asm/uaccess.h>
#include <linux/rcupdate.h>     //rcu_read_lock
#include <linux/proc_fs.h>	/** This is for procfs **/
/** spin lock essential here **/
#include <net/sock.h>
#include <linux/netlink.h>
#include <linux/skbuff.h>
#include <linux/spinlock.h>
#include <linux/netdevice.h>
#include <linux/suspend.h>
/** get Indicator PID */
static struct file_operations indicator_file_ops =
{
		.read = indicator_procfs_read,
		.write = indicator_procfs_write,
		.open = NULL,
		.release = NULL
};
static ssize_t indicator_procfs_write(struct file *file, const char *buffer, size_t len, loff_t * off)
{
	copy_from_user(procfs_buffer, buffer, procfs_buffer_size);
	sscanf(procfs_buffer, "%d", &pid);
	indicator_task = pid_task(find_pid_ns(pid, &init_pid_ns), PIDTYPE_PID);
}
/** Handle for netdevice */
struct notifier_block netdevice_notifier = {
		.notifier_call = netdevice_event_handler
};

static int netdevice_event_handler(struct notifier_block *unused, unsigned long event, void *ptr)
{
	switch (event) {
		case NETDEV_CHANGEMTU:
			break;
		case NETDEV_CHANGEADDR:
			break;
		case NETDEV_CHANGE:
			send_sig_to_user(NETWORK_NOTIFY);
			break;
		case NETDEV_DOWN:
			break;
		case NETDEV_UP:
			break;
	}
	return NOTIFY_DONE;
}
/** Handle for power managerment */
struct notifier_block pmdevice_notifier = {
		.notifier_call = pm_envent_handler 
};
static int pm_envent_handler(struct notifier_block *unused, unsigned long event, void *ptr)
{
	switch (event) {
		case PM_HIBERNATION_PREPARE:
			break;
		case PM_POST_HIBERNATION:
			break;
		case PM_SUSPEND_PREPARE:
			send_sig_to_user(SUSPEND_SIGNAL);
			break;	
		case PM_POST_SUSPEND:
			send_sig_to_user(RESUME_SIGNAL);
			break;	
		case PM_RESTORE_PREPARE:
			break;	
		case PM_POST_RESTORE:
			break;	
	}
	return NOTIFY_DONE;
}
/** Export function */
static void send_sig_to_user(unsigned long data)
{
	if (indicator_task != NULL) {
		indicator_sig_info.si_signo = SIG_INDICATOR_SIZE;
		indicator_sig_info.si_code =  SI_QUEUE;
		indicator_sig_info.si_int = data;
		send_sig_info(SIG_INDICATOR_SIZE, &indicator_sig_info, indicator_task)		
	} 
}
EXPORT_SYMBOL(send_sig_to_user);
/** Init module */
static int signal_init(void)
{
	// monitor proc file to get PID of indicator 
	proc_file = proc_create(PROCFS_FILE_NAME,0644,NULL,&indicator_file_ops);
	// register netdevice event
	ret = register_netdevice_notifier(&netdevice_notifier);
	// register pm event
	ret = register_pm_notifier(&pmdevice_notifier);
	if (ret) {
		printk(KERN_INFO "failed request_irq");
	}
}
/** Exit module */
static void signal_exit(void)
{
	unregister_netdevice_notifier(&netdevice_notifier);
	unregister_pm_notifier(&pmdevice_notifier);
}
MODULE_AUTHOR("PhuocNM"); 
MODULE_LICENSE("GPL");
module_init(signal_init);
module_exit(signal_exit);
