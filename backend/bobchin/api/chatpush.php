<?
include_once('../auth/settings.php');

$_meetid=$_POST['meetid'];
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

    $query= "SELECT users FROM meetings WHERE meetID='$_meetid'";
    $result = mysqli_query($con,$query);
    $users = mysqli_fetch_assoc($result)['users'];
    $users = explode("|",$users);
    for($i=1;$i<count($users);$i++){
        $query = "INSERT INTO pushmsg VALUES('','밥친 알림','새 채팅이 도착했습니다!',$users[$i],$_meetid,$con)";
    }
}
catch(Exception $e){
    echo "Unauthorized";
    exit;
}
?>