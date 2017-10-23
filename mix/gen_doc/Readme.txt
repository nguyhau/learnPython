/** Requiment*/
 - The GNU tools flex, bison, libiconv and GNU make, and strip
 - In order to generate a Makefile for your platform, you need cmake version 2.8.12 or later.
/** Install step by step */
    git clone https://github.com/doxygen/doxygen.git
    cd doxygen
    mkdir build
    cd build
    cmake -G "Unix Makefiles" ..
    make
    make install
/** Use doxygen to gen document:  */    
    doxygen <config_file>