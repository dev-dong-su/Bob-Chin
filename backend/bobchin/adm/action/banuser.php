<?
include("../auth/rcvcode.php");

$query = "UPDATE user SET auth_level='0' WHERE userid='$_GET[id]'";
mysqli_query($con,$query);
?>
<script>
    window.location="../users.php";
</script>