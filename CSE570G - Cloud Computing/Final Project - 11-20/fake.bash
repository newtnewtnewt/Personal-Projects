balanceespublicIPs=$(aws ec2 describe-instances --filter Name=instance.group-id,Values=sg-015a1e18e5c69bdd1 --query "Reservations[*].Instances[*].PublicIpAddress" --output=text | sed -E 's/[[:space:]]+/\n/g'
)
echo "$balanceespublicIPs"
num=1
for i in $balanceespublicIPs; do
        fulltag="${num} dunnnm2"
        ssh -o StrictHostKeyChecking=no ubuntu@${i} "sudo sh -c \"echo $fulltag >/var/www/html/id.txt\""
        num=$((num+1))
done

