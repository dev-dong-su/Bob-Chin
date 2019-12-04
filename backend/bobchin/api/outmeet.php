<?
include_once('../auth/settings.php');
$_meetid = $_POST['meetid'];

if(!isset($last_accesstoken)) {echo "Unauthorized"; exit;}
$client->setAccessToken($last_accesstoken);
if($expired) {
    $_token = $last_accesstoken = RefreshToken($con, $client, $refreshtoken);
}

$google_oauth = new Google_Service_Oauth2($client);
try{
    $google_account_info = $google_oauth->userinfo->get();
    $email = $google_account_info->email;
    $name = $google_account_info->name;
    $id = $google_account_info->id;

    $query = "SELECT headuser FROM meetings WHERE headuser='$email'";
    $result = mysqli_query($con, $query);
    $headuser = mysqli_fetch_assoc($result)['headuser'];
    if($headuser==$email){
        $query = "DELETE FROM meetings WHERE meetID='$_meetid'";
        $result = mysqli_query($con, $query);
    }
    else{
        $query = "UPDATE meetings SET users = REPLACE(users,'$email|','') WHERE meetID='$_meetid'";
        $result = mysqli_query($con, $query);
    }
    
    //no response
}
catch(Exception $e){
    echo "Unauthorized";
    exit;
}
?>