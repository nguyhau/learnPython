# Install
	Copy SquishCocoSetup_3.4.1_Linux_x86_64.run from \\10.116.16.22\public\software\CoverageTool\Squish Coco\V3.4.1
	Run ./SquishCocoSetup_3.4.1_Linux_x86_64.run
	Select "Local", Register License on Server 10.116.16.22:49344
# How to use
# Refer https://doc.froglogic.com/squish-coco/latest/tutorial.html#sec16
	./instrumented make main
	export $PATH=/opt/SquishCoco/bin/:$PATH
	coveragebrowser -m main.csmes -e main.csexe
