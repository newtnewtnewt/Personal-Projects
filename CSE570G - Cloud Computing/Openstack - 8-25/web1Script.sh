#!/bin/sh
echo "Running apt update"
apt update
echo "Installing nginx"
apt install -y nginx
echo "installing files (clone)"
cd /var/www
rm -rf html
git clone https://gitlab.csi.miamioh.edu/campbest/cse470g-web1.git html
echo "Restarting nginx"
systemctl restart nginx

