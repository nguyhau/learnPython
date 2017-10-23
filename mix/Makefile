#gcc -g src/*.c -o indicator -I include -lpthread -lX11 -lXrandr -ltzcs -ltzcs-security -ludev `pkg-config --cflags --libs gtk+-2.0`
# Set app name
	TARGET=indicator
# Compiler
	#CC = gcc
# Debug
# Warnings
	WARN=-Wall -g
# Linker
	INCLUDE_DIRS= include
	VPATH = src include
	LD_TZCS= pthread X11 Xrandr tzcs tzcs-security udev
	CFLAGS += $(addprefix -I $(PWD)/,$(INCLUDE_DIRS))
	LDFLAGS+= $(addprefix -l,$(LD_TZCS)) 
# Add gtk package to linker
	LINUX_PACKAGES:= gtk+-2.0
	PACKAGE_CFLAGS := $(foreach pkg,$(LINUX_PACKAGES),$(shell pkg-config --cflags $(pkg)))
	PACKAGE_LDFLAGS := $(foreach pkg,$(LINUX_PACKAGES),$(shell pkg-config --libs $(pkg)))
	CFLAGS += $(PACKAGE_CFLAGS)
	LDFLAGS += $(PACKAGE_LDFLAGS)
# Config
	BINARYDIR= build
	SOURCES = $(shell ls 'src')	
	OBJECTS = $(subst .c,.o,$(SOURCES))
	DEPS = helper.h
	ALL_OBJS:= $(addprefix $(BINARYDIR)/, $(OBJECTS))
# Create
$(BINARYDIR)/$(TARGET): $(ALL_OBJS) 
	$(CC) -o $@ $^ $(LDFLAGS)
$(BINARYDIR)/%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS) $(WARN)
clean:
	rm -f $(BINARYDIR)/*
