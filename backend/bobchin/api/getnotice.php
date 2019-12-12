<?
include_once('../auth/settings.php');

$google_oauth = new Google_Service_Oauth2($client);
try{
    $google_account_info = $google_oauth->userinfo->get();
    $email = $google_account_info->email;
    $name = $google_account_info->name;
    $id = $google_account_info->id;

    $query = "SELECT * FROM notice";
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