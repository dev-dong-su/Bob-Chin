<?php
include_once('settings.php');

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

    $returnJSON = array();
    if(mysqli_fetch_assoc($result)['cnt'] > 0){
      	$returnJSON['user']="1";
      	$returnJSON['email']=$email;
		$returnJSON['name']=$name;
		$returnJSON['accesstoken']=$token['access_token'];
		$query = "UPDATE user SET userid='$id',accesstoken='$token[access_token]',name='$name',last_accesstoken='$token[access_token]' WHERE email='$email";
    	mysqli_query($con, $query);
    }
    else{
        $returnJSON['user']="0";
        $returnJSON['email']=$email;
		$returnJSON['name']=$name;
		$returnJSON['accesstoken']=$token['access_token'];
		$query = "INSERT INTO user VALUES('$id','$token[access_token]','$token[refresh_token]','$name','$email','','','$token[access_token]')";
    	mysqli_query($con, $query);
	}
	echo json_encode($returnJSON);


} else {
    echo "Unauthorized";
}
?>