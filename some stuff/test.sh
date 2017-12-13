#!/bin/bash
#############################################
# Create depend file
############################################
filter_depend(){
	filename="$1.depend"
	if [ -e $filename ]
	then	
		rm $filename
	fi
	#While loop to read line by line
	while read -r line
	do
	    readLine=$line
	    #If the line starts with ST then echo the line
		for word in $line; do
	        echo "$word"|grep -v "usr"|grep -v '\\'|grep -v ':'>>$filename
	    done
	done < "$1"
	cp $filename "/home/nguyhau/try/depend"
}

#############################################
# Create depend file
#############################################
main(){
	while read -r line
	do
		filter_depend $line
	done < "$1"
}
main $1
