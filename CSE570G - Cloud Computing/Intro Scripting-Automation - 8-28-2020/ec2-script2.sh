#!/bin/bash
# Name: Noah Dunn
# Professor: Dr. Campbell
# Class: DevOPS + Cloud Computing
# Date: 8/27/2020
# Assignment: EC2 Automation
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
sed -E '/\Simple Web Site./a \  \<div class="site">This is SITE 2</div>' /var/www/html/index.html |  
sed -E 's/<\/body>/<div class="footer">CSE470g<\/div><\/body>/' | 
sed -E 's/Campbest|campbest/dunnnm2/' | 
sed -E 's/Scott Campbell/Noah Dunn/' > /var/www/html/index.html.temp && mv /var/www/html/index.html.temp /var/www/html/index.html
