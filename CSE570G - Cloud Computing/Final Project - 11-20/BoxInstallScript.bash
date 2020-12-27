#!/bin/bash
apt-get update -y && apt-get install ansible -y
apt-get install nginx -y
curl -ik "https://external-dns.csi.miamioh.edu/ddns1.php?hkey=b6f97b62fec90dd7e3549c5b27a1d5ed&dnsname=dunnnm2.aws"
