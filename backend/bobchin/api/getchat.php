<?
include_once('../auth/settings.php');
$_meetid = $_GET['meetid'];
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

    $query = "SELECT userid,message,datetime FROM chat WHERE meetID='$_meetid'";
    $result = mysqli_query($con, $query);

    for($i=0;$i<mysqli_num_rows($result);$i++){
        $row=mysqli_fetch_assoc($result);
        $returnJSON[$i]=$row;
    }
    echo json_encode($returnJSON);
}
catch(Exception $e){
    echo "Unauthorized";
    exit;
}
?>