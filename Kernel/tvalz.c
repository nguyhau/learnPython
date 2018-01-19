// Read notify event by INFO method.
void tvalz_acpi_getevent(struct tvalz_acpi_dev* p_tacpi_dev)
{
	u32    value = 0;
	struct tvalz_key_entry *p_key = NULL;
	int    ret = TVALZ_OPS_SUCCESS;

	tos_debug(TOSHIBA_INFO "%s is called.\n", __func__);

	do{
		ret = tvalz_getinfo(p_tacpi_dev, &value);
		// Send keyboard event 
		tos_debug(TOSHIBA_INFO "System event 0x%x.\n", value);
		if (value != 0) {
			send_sig_to_user(value);
		}
	}
}