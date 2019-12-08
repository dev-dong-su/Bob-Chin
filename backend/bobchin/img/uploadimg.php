<?php
include ("../auth/dbconnect.php");
$_datetime = date("Y-m-d H:i:s", time());
$_id = strtolower(generateRandomString(20));
$fileext = pathinfo($_FILES['imagefile']['name'], PATHINFO_EXTENSION);
$_filename= strtolower($_id.".".$fileext);

$target_dir = "images/";
$target_file = $target_dir . $_filename;
$uploadOk = 1;


if(isset($_FILES["imagefile"])) {
    $check = getimagesize($_FILES["imagefile"]["tmp_name"]);
    if($check !== false) {
        $uploadOk = 1;
    } else {
        echo "1";//이미지 아님
        $uploadOk = 0;
        exit;
    }
}

//업로드 
if ($uploadOk) {
    if (move_uploaded_file($_FILES["imagefile"]["tmp_name"], $target_file)) {
        $query = "INSERT INTO images VALUES('$_id','images/$_filename','$_datetime')";
        $result = mysqli_query($con,$query);

        if($_GET['for']=="notice"){
            $query = "INSERT INTO notice VALUES('','http://bobchin.cf/img/getimg.php?img=$_id','$_datetime')";
            mysqli_query($con,$query);
            echo "<script>window.location='../adm/notice.php'</script>";
        }
        else{
            echo "0";
        }
    } else {
        echo "999"; //알수없는 오류. 권한, 파일 읽기 불가.. 등등
    }
}

function generateRandomString($length = 10) {
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
}
?>