<?
include("../auth/rcvcode.php");

$query = "DELETE FROM images WHERE id='$_GET[id]'";
mysqli_query($con,$query);
?>
<script>
    window.location="../images.php";
</script>