################################################################################################################
# Copyright : luyentm
# 1.       Mặc định build vào nửa đêm
# 2.       Mặc định build commit mới nhất
# 3.       Script tự động xóa hết thư mục build và build lại từ đầu
# 4.       Script tự động gửi mail thông báo cho team sau khi build xong.
# 5.       Script có thể được dùng trong trường hợp khẩn cấp phải hỗ trợ khách hàng trong khoảng thời gian ngắn,
################################################################################################################
#				CONFIGURATION
################################################################################################################
TARGET_BRANCH[0]="daisy-dspc-tsdv-vdi-citrix-vmware-common"		# branch to build
###
# 08/05/2017. luyentm: chỉ để build branch common
##
#TARGET_BRANCH[1]="daisy-dspc-tsdv-vdi-vmware-A9J"	# branch to build
TARGET_BUILD_SMALL_CORE="linux-base"				# command build small core
TARGET_BUILD_BIG_CORE="core-image-base"				# command build core
FOLDER_SMALL_CORE="build-small-core"				# folder build small core
FOLDER_BIG_CORE_VMWARE="build-big-core-vmware"		# folder build big core
FOLDER_BIG_CORE_CITRIX="build-big-core-citrix"		# folder build big core
BUILD_PATH="$HOME/A5JT-luyentm"						# folder source working
SCRIPT_PATH="$BUILD_PATH/autobuild"					# folder store script
STORE_PATH="$HOME/build_store"						# folder store build result
LOG_SMALL_CORE="log-build-small-core"				# file name to log small core
LOG_BIG_CORE_VMWARE="log-build-big-core-vmware"					# file name to log big core
LOG_BIG_CORE_CITRIX="log-build-big-core-citrix"
LOG_SEND_MAIL="log-send-mail"						# summary inportant log and send to Romlinux Team
DATE_BUILD="`date +%F`"								# date time

TIME_BUILD_SMALL_CORE=0							# if build error, try with 3 times
TIME_BUILD_BIG_CORE_VMWARE=0						# if build error, try with 3 times
TIME_BUILD_BIG_CORE_CITRIX=0

STORE_DATA_PATH=""
STORE_LOG_PATH=""
CURRENT_BRANCH=""

################################################################################################################
#				FUNCTION DEFINE
################################################################################################################
# input : none
# output: none
# des	: clean small and big core build tree
clean_all (){
	cd $BUILD_PATH
	yes | rm -rf $FOLDER_SMALL_CORE
	yes | rm -rf $FOLDER_BIG_CORE_VMWARE
	yes | rm -rf $FOLDER_BIG_CORE_CITRIX

	mkdir $FOLDER_SMALL_CORE
	mkdir $FOLDER_BIG_CORE_VMWARE
	mkdir $FOLDER_BIG_CORE_CITRIX
}

################################################################################################################
# input : none
# output: none
# des	: pull source code TZCS
checkout_branch(){
	echo "Target branch : $CURRENT_BRANCH" >> $BUILD_PATH/$LOG_SEND_MAIL
	echo ""
	cd "$BUILD_PATH/poky/meta-tzcs"
	git reset --hard HEAD^
	git reset --hard HEAD^
	git checkout $CURRENT_BRANCH &> /dev/null
	git pull
	git show --quiet HEAD >> "$BUILD_PATH/$LOG_SEND_MAIL"
	COMMIT_ID=`git show --quiet | head -1 | cut -d' ' -f2`
	STORE_DATA_PATH="$STORE_PATH/$CURRENT_BRANCH/$COMMIT_ID"
	STORE_LOG_PATH="$STORE_DATA_PATH/$DATE_BUILD"
	mkdir -p $STORE_LOG_PATH
	echo "*************************************************" >> $BUILD_PATH/$LOG_SEND_MAIL
}

################################################################################################################
# input : none
# output: none
# des	: build small core
build_small_core (){
	cd "$BUILD_PATH"
	echo "[START-BUILD_SMALLCORE]:	`date` " >> $BUILD_PATH/$LOG_SEND_MAIL
	# setup conf file for bitbake
	export TEMPLATECONF=meta-tzcs/meta-intel-wifi/conf
	source poky/oe-init-build-env $BUILD_PATH/$FOLDER_SMALL_CORE &>> $BUILD_PATH/$LOG_SMALL_CORE

	while [[ $TIME_BUILD_SMALL_CORE -lt 3 ]]; do
		TIME_BUILD_SMALL_CORE=`expr $TIME_BUILD_SMALL_CORE + 1`
		echo -e "--- Times build $TIME_BUILD_SMALL_CORE ---" >> $BUILD_PATH/$LOG_SEND_MAIL
		# cleanall build, build depences
		# clean LOG_SMALL_CORE file
		rm -rf $BUILD_PATH/$LOG_SMALL_CORE
		bitbake -ccleanall linux-base &> /dev/null
		bitbake $TARGET_BUILD_SMALL_CORE &>>  $BUILD_PATH/$LOG_SMALL_CORE
		re1=`python $SCRIPT_PATH/search_errors_log.py $BUILD_PATH/$LOG_SMALL_CORE`

		if [[ ! -z $re1 ]];	then
			echo -e "\nError found :(" >> $BUILD_PATH/$LOG_SEND_MAIL
			if [[ -f $re1 ]]; then
				echo "Contents of $re1" >> $BUILD_PATH/$LOG_SEND_MAIL
				cat $re1 >> $BUILD_PATH/$LOG_SEND_MAIL
			else
				echo "Error at $re1" >> $BUILD_PATH/$LOG_SEND_MAIL
			fi
		else
			echo -e "\nNo error found :)" >> $BUILD_PATH/$LOG_SEND_MAIL
			cp /home/luyentm/A5JT-luyentm/build-small-core/tmp/deploy/images/intel/bzImage-intel.bin $STORE_DATA_PATH
 cp /home/luyentm/A5JT-luyentm/build-small-core/tmp/deploy/images/intel/modules-intel.tgz $STORE_DATA_PATH/modules-intel.tgz

			break;
		fi
	done
	mv "$BUILD_PATH/$LOG_SMALL_CORE" $STORE_LOG_PATH
	echo "[COMPLETE]:	`date` " >> $BUILD_PATH/$LOG_SEND_MAIL
	echo "*******************************************" >> $BUILD_PATH/$LOG_SEND_MAIL
}

################################################################################################################
# input : none
# output: none
# des	: build big core
build_big_core (){
	cd "$BUILD_PATH"
	# delete build small folder
	rm -rf $FOLDER_SMALL_CORE
	echo "[START-BUILD-BIGCORE-VMWARE]:	`date` " >> $BUILD_PATH/$LOG_SEND_MAIL
	
	export TEMPLATECONF=meta-tzcs/meta-intel-vdi-vmware/conf
	source poky/oe-init-build-env $BUILD_PATH/$FOLDER_BIG_CORE_VMWARE &>> $BUILD_PATH/$LOG_BIG_CORE_VMWARE

	while [[ $TIME_BUILD_BIG_CORE_VMWARE -lt 3 ]]; do
		TIME_BUILD_BIG_CORE_VMWARE=`expr $TIME_BUILD_BIG_CORE_VMWARE + 1`
		echo -e "--- Times build $TIME_BUILD_BIG_CORE_VMWARE ---" >> $BUILD_PATH/$LOG_SEND_MAIL
		# cleanall build, build depences
		# clean LOG_BIG_CORE file
		rm -rf $BUILD_PATH/$LOG_BIG_CORE_VMWARE

		bitbake -ccleanall $TARGET_BUILD_BIG_CORE_VMWARE &> /dev/null
		# build
		bitbake $TARGET_BUILD_BIG_CORE &>>  $BUILD_PATH/$LOG_BIG_CORE_VMWARE
		re1=`python $SCRIPT_PATH/search_errors_log.py $BUILD_PATH/$LOG_BIG_CORE_VMWARE`

		if [[ ! -z $re1 ]];	then
			echo -e "\nERROR FOUND :(" >> $BUILD_PATH/$LOG_SEND_MAIL
			if [[ -f $re1 ]]; then
				echo "Contents of $re1" >> $BUILD_PATH/$LOG_SEND_MAIL
				cat $re1 >> $BUILD_PATH/$LOG_SEND_MAIL
			else
				echo "ERROR at $re1" >> $BUILD_PATH/$LOG_SEND_MAIL
			fi
		else
			echo -e "\nNo error found :)" >> $BUILD_PATH/$LOG_SEND_MAIL
			cp /home/luyentm/A5JT-luyentm/build-big-core-vmware/tmp/deploy/images/intel/core-image-base-intel.tar.gz $STORE_DATA_PATH/core-image-intel-vmware.tar.gz
    		cp /home/luyentm/A5JT-luyentm/build-big-core-vmware/tmp/deploy/images/intel/modules-intel.tgz $STORE_DATA_PATH/modules-intel-vmware.tgz
			break;
		fi
	done

	# build big core citrix
	cd "$BUILD_PATH"
	# delete build big core vmware folder
	rm -rf $FOLDER_BIG_CORE_VMWARE
	echo "[START-BUILD-BIGCORE-CITRIX]:	`date` " >> $BUILD_PATH/$LOG_SEND_MAIL

	export TEMPLATECONF=meta-tzcs/meta-intel-vdi-citrix/conf
	source poky/oe-init-build-env $BUILD_PATH/$FOLDER_BIG_CORE_CITRIX &>> $BUILD_PATH/$LOG_BIG_CORE_CITRIX

	while [[ $TIME_BUILD_BIG_CORE_CITRIX -lt 3 ]]; do
		TIME_BUILD_BIG_CORE_CITRIX=`expr $TIME_BUILD_BIG_CORE_CITRIX + 1`
		echo -e "--- Times build $TIME_BUILD_BIG_CORE_CITRIX ---" >> $BUILD_PATH/$LOG_SEND_MAIL
		# cleanall build, build depences
		# clean LOG_BIG_CORE file
		rm -rf $BUILD_PATH/$LOG_BIG_CORE_CITRIX

		bitbake -ccleanall $TARGET_BUILD_BIG_CORE_VMWARE &> /dev/null
		# build
		bitbake $TARGET_BUILD_BIG_CORE &>>  $BUILD_PATH/$LOG_BIG_CORE_CITRIX
		re1=`python $SCRIPT_PATH/search_errors_log.py $BUILD_PATH/$LOG_BIG_CORE_CITRIX`

		if [[ ! -z $re1 ]];	then
			echo -e "\nERROR FOUND :(" >> $BUILD_PATH/$LOG_SEND_MAIL
			if [[ -f $re1 ]]; then
				echo "Contents of $re1" >> $BUILD_PATH/$LOG_SEND_MAIL
				cat $re1 >> $BUILD_PATH/$LOG_SEND_MAIL
			else
				echo "ERROR at $re1" >> $BUILD_PATH/$LOG_SEND_MAIL
			fi
		else
			echo -e "\nNo error found :)" >> $BUILD_PATH/$LOG_SEND_MAIL
			cp /home/luyentm/A5JT-luyentm/build-big-core-citrix/tmp/deploy/images/intel/core-image-base-intel.tar.gz $STORE_DATA_PATH/core-image-base-intel-citrix.tar.gz
    		cp /home/luyentm/A5JT-luyentm/build-big-core-citrix/tmp/deploy/images/intel/modules-intel.tgz $STORE_DATA_PATH/modules-intel-citrix.tgz
			break;
		fi
	done
	mv "$BUILD_PATH/$LOG_BIG_CORE_VMWARE" $STORE_LOG_PATH
	mv "$BUILD_PATH/$LOG_BIG_CORE_CITRIX" $STORE_LOG_PATH
	echo "[COMPLETE]:	`date` " >> $BUILD_PATH/$LOG_SEND_MAIL
	echo "***************************************************" >> $BUILD_PATH/$LOG_SEND_MAIL
}

################################################################################################################
main (){
	# try to build all branch 
	for i in ${TARGET_BRANCH[@]}; do
		# clean all folder build
		clean_all
		CURRENT_BRANCH=$i
		checkout_branch
		build_small_core
		build_big_core
	done

	echo " " >> $BUILD_PATH/$LOG_SEND_MAIL
	echo "NOTE: Building results will be stored in server, if you want testing, please find out as follow:" >> $BUILD_PATH/$LOG_SEND_MAIL
	echo "Server : romlinux@10.116.41.71" >> "$BUILD_PATH/$LOG_SEND_MAIL"
	echo "Password : 1" >> "$BUILD_PATH/$LOG_SEND_MAIL"
	echo "Data path : /home/romlinux/chroot-debian-luyentm/home/luyentm/build_store/" >> $BUILD_PATH/$LOG_SEND_MAIL
	echo "Log path: /home/romlinux/chroot-debian-luyentm/home/luyentm/build_store/" >> $BUILD_PATH/$LOG_SEND_MAIL
	echo "" >> $BUILD_PATH/$LOG_SEND_MAIL
	echo "************************************************************************" >> $BUILD_PATH/$LOG_SEND_MAIL
	echo "Please must not change any configure in this server!!!" >> $BUILD_PATH/$LOG_SEND_MAIL
	python $SCRIPT_PATH/sendlog.py
	mv "$BUILD_PATH/$LOG_SEND_MAIL" $STORE_LOG_PATH
}
################################################################################################################
#			MAIN FUNCTION
################################################################################################################
#HEADER
echo -e  "[AutoBuild version 3.0 - Testing]" > $BUILD_PATH/$LOG_SEND_MAIL
echo "********************************************************************" >> $BUILD_PATH/$LOG_SEND_MAIL
main
