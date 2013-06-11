<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LOAN ESTABLISHMENT PAGE</title>
</head>
<body>
	
	<h1>LOAN ESTABLISHMENT STEP</h1>
	<s:form action="loan_establishment">
	<s:textfield name="credit_amount" label="LOAN AMOUNT REQUIRED" />
	<s:textfield name="customer_name" label="NAME" />
	<s:textfield name="customer_address" label="ADDRESS" />
	<s:textfield name="loan_due_date" label="LOAN_EXPIRY_DATE" />
	<s:textfield name="rfc" label="RFC" />
	<s:submit value="submit" />
</s:form>
</body>
</html>