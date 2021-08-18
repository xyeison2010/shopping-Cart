function confirmDelete(id){
	$('#deleteModal').modal('show');
	$("#userIdHiddenInput").val(id);
}

function deleteUser(){
	var id = $("#userIdHiddenInput").val();
    window.location = "/deleteUser/"+id;
}