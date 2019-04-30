<%@ page contentType="text/html; charset=euc-kr" %>

<%--
<%@ page import ="com.model2.mvc.service.domain.Product" %>
<%@ page import ="com.model2.mvc.service.domain.User" %>

<%
	Product product = (Product)session.getAttribute("vo");
	User user = (User)session.getAttribute("user");
%>

 --%>
 
<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=0" method="post">

다음과 같이 구매가 되었습니다.

<table border=1>
	<tr>
		<td>구매 제품</td>
		<td>${sessionScope.vo.prodName}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매 개수</td>
		<td>${param.quantity}</td>
		<td></td>
	</tr>
	<tr>
		<td>총 구매가격</td>
		<td>${(param.quantity)*(sessionScope.vo.price)}</td>
		<td></td>
	</tr>
	
	<tr>
		<td>구매자아이디</td>
		<td>${user.userId}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td>
			${param.paymentOption}
		</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<td>${param.receiverName}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td>${param.receiverPhone}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<td>${param.receiverAddr}</td>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td>${param.receiverRequest}</td>
		<td></td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<td>${param.receiverDate}</td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>