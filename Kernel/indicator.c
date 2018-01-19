/** Hanld for all event */
void handle_sig_event(int signal_event) 
{
	switch (signal_event) { /* handle for press keyboard event*/
    case FN_F3:
        //do something ;
		break;
    case FN_F4:
        //do something ;
		break;
	default:
		break;
	}
	if (signal_event == NETWORK_NOTIFY) { /* handle for network event */
		//do something 
	}else {
        if (signal_event == VGA_NOTIFY) { /* handle for VGA/HDMI event */
			//do something 
		}
	}
}
/** Process for signal event */
void send_notification_event(int n, siginfo_t *info, void *unused) 
{
    /* Send notifier event */
    handle_sig_event(info->si_int);
}
void signal_event_handler(void *thread_arg)
{
	// Write PID 
	sprintf(bufTaskId, "%i", (int)syscall(__NR_gettid));
	st_event.configfd = open(PROCFS_FILE_NAME_PATH, O_RDWR);
	write(st_event.configfd, bufTaskId, strlen(bufTaskId) + 1)
	// Register signal event	 
    st_event.sigPlugIn.sa_sigaction = send_notification_event;
    st_event.sigPlugIn.sa_flags = SA_SIGINFO;
	sigaction(SIG_INDICATOR_SIZE, &st_event.sigPlugIn, NULL);
	/* Monitor TVALZ file*/
    do {
        length = read(st_event.configfd, &readbuff, 1);
    } while (length == 0 || errno == EINTR);

    printf("The length value is %d\r\n",length);
    printf("closing file now..\r\n");
    close(st_event.configfd);
}
/** Process for VGA/HDMI event */
void monitor_system_status()
{
	struct udev *udev;
    struct udev_device *dev;
	struct udev_monitor *mon = udev_monitor_new_from_netlink(udev, "udev");
    udev_monitor_filter_add_match_subsystem_devtype(mon, "drm", NULL);
    udev_monitor_enable_receiving(mon);
    int fd = udev_monitor_get_fd(mon);
    while (1) {
        fd_set fds;
        int ret;        
        FD_ZERO(&fds);    
        FD_SET(fd, &fds);
        ret = select(fd+1, &fds, NULL, NULL, NULL);
        if (ret > 0 && FD_ISSET(fd, &fds)) {
            dev = udev_monitor_receive_device(mon);
            if (dev) {
				udev_device_unref(dev);
                handle_sig_event(VGA_NOTIFY);
			}
		}
	}
}