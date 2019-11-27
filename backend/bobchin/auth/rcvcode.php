<?php
include_once('settings.php');
$_datetime = date("Y-m-d H:i:s", time());

if (isset($_POST['authcode'])) {
    $token = $client->fetchAccessTokenWithAuthCode($_POST['authcode']);
    $client->setAccessToken($token['access_token']);

    $google_oauth = new Google_Service_Oauth2($client);
    $google_account_info = $google_oauth->userinfo->get();
    $email = $google_account_info->email;
	$name = $google_account_info->name;
	$id = $google_account_info->id;

    $query = "SELECT count(*) as cnt FROM user WHERE email='$email'";
    $result = mysqli_query($con, $query);
    $cnt = mysqli_fetch_assoc($result)['cnt'];

    $query = "SELECT * FROM user WHERE email='$email'";
    $result = mysqli_query($con, $query);
    $row = mysqli_fetch_assoc($result);

    $returnJSON = array();
    if($cnt > 0){
      	$returnJSON['user']="1";
      	$returnJSON['email']=$email;
        $returnJSON['name']=$name;
        $returnJSON['authlevel']=$row['auth_level'];
        $returnJSON['accesstoken']=$token['access_token'];
		$query = "UPDATE user SET userid = '$id',accesstoken='$token[access_token]',name='$name',last_accesstoken='$token[access_token]',last_renew='$_datetime' WHERE email='$email'";
    	mysqli_query($con, $query);
    }
    else{
        $returnJSON['user']="0";
        $returnJSON['email']=$email;
		$returnJSON['name']=$name;
        $returnJSON['accesstoken']=$token['access_token'];
        $returnJSON['authlevel']=$row['auth_level'];
		$query = "INSERT INTO user VALUES('$id','$token[access_token]','$token[refresh_token]','$name','$email','','','$token[access_token]','$_datetime')";
    	mysqli_query($con, $query);
	}
	echo json_encode($returnJSON);


} else {
    echo "Unauthorized";
}
?>