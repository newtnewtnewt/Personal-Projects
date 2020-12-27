# Name: Noah Dunn
# Professor: Dr. Campbell 
# Class: CSE 570G
# Date: 8/25/2020
# Assignment: Openstack Intro
#!/bin/bash
openstack server create --image bionic --flavor m1.small --network 470g-internal --key-name dunnnm2 --user-data web1Script.sh
dunnnm2
