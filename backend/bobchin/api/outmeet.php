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

    $query = "SELECT headuser,users,meetname,meetid FROM meetings WHERE meetID='$_meetid'";
    $result = mysqli_query($con, $query);
    $row = mysqli_fetch_assoc($result);
    if($row['headuser'] == $email){
        $users=explode("|",$row['users']);
        for($i=2;$i<count($users);$i++)
            PostMessage('밥친 알림','밥짱이 '.$row['meetname'].' 모임을 취소했습니다.',$users[$i],$row['meetid'],$con);
        $query = "DELETE FROM meetings WHERE meetID='$_meetid'";
        $result = mysqli_query($con, $query);
    }
    else{
        PostMessage('밥친 알림','밥친 '.$name.'이 모임을 나갔습니다.',$row['headuser'],$row['meetid'],$con);
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