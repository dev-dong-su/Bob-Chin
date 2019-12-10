<?
include("../auth/rcvcode.php");

$query = "DELETE FROM notice WHERE notinum='$_GET[id]'";
mysqli_query($con,$query);
?>
<script>
    window.location="../notice.php";
</script>