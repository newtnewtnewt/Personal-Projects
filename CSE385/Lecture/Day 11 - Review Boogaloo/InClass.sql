-----=================Q1
SELECT o.name, o.phoneNumber, p.address 
FROM Property p, PropertyOwners po, Owner o 
WHERE p.propertyId = po.propertyId AND po.ownerId = o.ownerId
ORDER BY p.address, o.name
----==================Q2
SELECT r.renterName, p.address, pr.deposit,rd.depositReq, [depositOwed] = rd.depositReq - pr.deposit 
FROM PropertyRental pr, Renter r, RentalData rd, Property p
WHERE r.renterID = pr.renterId AND pr.rentalDataId = rd.rentalDataId AND rd.propertyId = p.propertyId
AND rd.depositReq - pr.deposit  > 0
ORDER BY r.renterName
----==================Q3
SELECT r.renterName, p.address, pr.deposit,rd.depositReq, [depositOwed] = rd.depositReq - pr.deposit 
FROM PropertyRental pr, Renter r, RentalData rd, Property p
WHERE r.renterID = pr.renterId AND pr.rentalDataId = rd.rentalDataId AND rd.propertyId = p.propertyId
AND rd.depositReq - pr.deposit  != 0
ORDER BY r.renterName
----==================Q4
SELECT r.renterName, p.address, pr.deposit,rd.depositReq, [depositOwed] = rd.depositReq - pr.deposit, 
[status] = CASE WHEN (rd.depositReq - pr.deposit) > 0 THEN 'Outstanding Account'
		        WHEN (rd.depositReq - pr.deposit) < 0 THEN 'Refund Needed'
		   ELSE 'OK'
		   END
FROM PropertyRental pr, Renter r, RentalData rd, Property p
WHERE r.renterID = pr.renterId AND pr.rentalDataId = rd.rentalDataId AND rd.propertyId = p.propertyId
ORDER BY r.renterName
----=================Q5
SELECT TOP(5) p.address, o.name FROM PropertyOwners po, Owner o, Property p
WHERE p.propertyId = po.propertyId AND o.ownerId = po.ownerId AND percentOwner = 100
ORDER BY p.address, o.name
----=================Q6
SELECT p.address, o.name FROM PropertyOwners po, Owner o, Property p
WHERE p.propertyId = po.propertyId AND o.ownerId = po.ownerId AND percentOwner = 100
ORDER BY p.address, o.name
OFFSET 5 ROWS
FETCH NEXT 5 ROWS ONLY
----=================Q7
SELECT p.address, r.renterName, [status] = 'No Rent Paid' 
FROM PropertyRental pr LEFT JOIN RentalPayment rp ON pr.propertyRentalId = rp.propertyRentalId 
JOIN Renter r ON pr.renterId = r.renterId 
JOIN RentalData rd ON rd.rentalDataId = pr.rentalDataId
JOIN Property p ON p.propertyId = rd.propertyId
WHERE rp.paymentDate IS NULL




