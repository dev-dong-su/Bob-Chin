<?
include_once('../auth/settings.php');
$_date = date("Y-m-d", time());
$_datetime = date("Y-m-d H:i:s", time());

$google_oauth = new Google_Service_Oauth2($client);
try{
    $google_account_info = $google_oauth->userinfo->get();
    $email = $google_account_info->email;
    $name = $google_account_info->name;
    $id = $google_account_info->id;

    $returnJSON=array();
    $condition="";
    if(isset($_GET['hidepassed'])){
        $condition .= "starttime>='$_datetime'";
    }
    if(isset($_GET['day'])){
        if($_GET['day']=="today")
            if($condition != "") $condition.= " AND ";
            $condition .= "INSTR(starttime,'$_date')";
    }
    if($condition != "") $condition = " AND ".$condition." ";
    $query = "SELECT * FROM meetings WHERE INSTR(users,'$email')$condition ORDER BY starttime DESC";
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