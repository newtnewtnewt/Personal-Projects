<?php

$curl = curl_init();

curl_setopt_array($curl, array(
  CURLOPT_URL => "http://dunnnm2.383.csi.miamioh.edu/cse383-f19-dunnnm2/rest1/rest.php/v1/user/",
  CURLOPT_RETURNTRANSFER => true,
  CURLOPT_ENCODING => "",
  CURLOPT_MAXREDIRS => 10,
  CURLOPT_TIMEOUT => 30,
  CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
  CURLOPT_CUSTOMREQUEST => "POST",
  CURLOPT_POSTFIELDS => "{\"uid\":\"test4\",\"email\":\"test@test.com\",\"password\":\"123\"}",
  CURLOPT_HTTPHEADER => array(
    "Accept: */*",
    "Accept-Encoding: gzip, deflate",
    "Content-Type: application/json",
    "Host: campbest.383.csi.miamioh.edu",
    "User-Agent: PostmanRuntime/7.19.0"
  ),
));

$response = curl_exec($curl);
$err = curl_error($curl);

curl_close($curl);

if ($err) {
  echo "cURL Error #:" . $err;
} else {
  echo $response;
}

echo "\n\n";
