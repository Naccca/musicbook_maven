<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored = "false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<script type="text/javascript">
	(function() {
		var burger = document.querySelector('.burger');
		var nav = document.querySelector('#'+burger.dataset.target);
		burger.addEventListener('click', function(){
		burger.classList.toggle('is-active');
			nav.classList.toggle('is-active');
		});
	
		document.querySelectorAll('.comma-list').forEach(ul => {
			var lis = ul.innerHTML.split(',');
			var result = '<li>' + lis.join('</li><li>') + '</li>';
			ul.innerHTML = result;
		});
	})();
</script>

<c:url var="homeUrl" value="/" />
<c:url var="artistsUrl" value="/artists" />
<c:url var="bandsUrl" value="/bands" />
<footer class="footer has-background-dark">
	<div class="container">
		<div class="level">
			<div class="level-left">
				<div class="level-item">
					<a class="title is-4 has-text-light" href="${homeUrl}" style="font-weight: bold;">musicbook</a>
				</div>
				<p class="subtitle is-6 has-text-light">© 2020 Nadežda Nisić. All right reserved.</p>
			</div>
			<div class="level-right">
				<a class="level-item has-text-light" href="${homeUrl}">Home</a>
				<a class="level-item has-text-light" href="${artistsUrl}">Artists</a>
				<a class="level-item has-text-light" href="${bandsUrl}">Bands</a>
			</div>
		</div>
	</div>
</footer>