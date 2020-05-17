<?php
require "db.php";
header("Cache-Control: no-store, no-cache, must-revalidate, max-age=0");
header("Cache-Control: post-check=0, pre-check=0", false);
header("Pragma: no-cache");

  $width=410; 
  $height=410; 

  Header("Content-type: image/gif"); 
  $im = imagecreate($width,$height); 
  $gray=ImageColorAllocate($im,225,225,225); 

  $black=ImageColorAllocate($im,0,0,0); 

  $points = getPoints();
  foreach ($points as $p) {
	  $a =print_r($p,true);
	  error_log($a);
	  $color = ImageColorAllocate($im,$p['r'],$p['g'],$p['b']);
	  imagefilledrectangle($im,$p['x'],$p['y'],$p['x1'],$p['y1'],$color);
  }
  imagePng($im); 
  ImageDestroy($im); 


?>
