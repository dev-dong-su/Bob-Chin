<?
include ("../auth/dbconnect.php");
$_img = $_GET['img'];

$query = "SELECT path FROM images WHERE id='$_img'";
$result = mysqli_query($con,$query);

$path = mysqli_fetch_assoc($result)['path'];

header('Content-Description: File Transfer');
header('Content-Type: image/png');
header('Content-Disposition: attachment; filename="'.basename($path).'"');
header('Expires: 0');
header('Cache-Control: must-revalidate');
header('Pragma: public');
header('Content-Length: ' . filesize($path));
readfile($path);
?>