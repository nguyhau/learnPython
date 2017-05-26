#!/bin/sh
sudo mount --bind -o rw,nosuid,nodev,noexec /dev/pts chroot1/dev/pts
sudo mount --bind -o rw,nosuid,nodev,noexec /dev/shm chroot1/dev/shm
sudo mount --bind /proc chroot1/proc

