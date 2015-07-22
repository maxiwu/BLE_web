<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>testing BLE complex</title>

<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/style.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.min.css"
	rel="stylesheet">

<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="${pageContext.request.contextPath}/resources/img/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="${pageContext.request.contextPath}/resources/img/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="${pageContext.request.contextPath}/resources/img/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="${pageContext.request.contextPath}/resources/img/apple-touch-icon-57-precomposed.png">
<link rel="shortcut icon" href="resources/img/favicon.png">

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="${pageContext.request.contextPath}/resources/img/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="${pageContext.request.contextPath}/resources/img/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="${pageContext.request.contextPath}/resources/img/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="${pageContext.request.contextPath}/resources/img/apple-touch-icon-57-precomposed.png">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.structure.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.theme.min.css">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-ui.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css">

<link
	href="${pageContext.request.contextPath}/resources/font-awesome-4.3.0/css/font-awesome.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/font-awesome-4.3.0/css/custom.css"
	rel="stylesheet">

<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.icon-large.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/bootstrap-toogle/css/bootstrap2-toggle.min.css"
	rel="stylesheet">

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/bootstrap-toogle/js/bootstrap2-toggle.min.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/sockjs.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/stomp.js"></script>

<script type="text/javascript">
	$(function() {

		/* var sock = new SockJS('http://localhost:8080/liveBLE/myHandler');

		sock.onmessage = function(e) {
		    console.log('message', e.data);
		    //alert("e.data");
		} */
	});
	var stompClient = null;

	function setConnected(connected) {
		document.getElementById('connect').disabled = connected;
		document.getElementById('disconnect').disabled = !connected;
		//document.getElementById('conversationDiv').style.visibility = connected ? 'visible'
		//		: 'hidden';
		//document.getElementById('response').innerHTML = '';
	}

	function connect() {
		var socket = new SockJS('/liveBLE/hello');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {
			setConnected(true);
			console.log('Connected: ' + frame);
			/* stompClient.subscribe('/topic/greetings',
					function(greeting) {
						showGreeting(JSON.parse(greeting.body).content);
					}); */

			stompClient.subscribe('/topic/data1', function(
					DataMessage) {
				showGreeting(JSON.parse(DataMessage.body).value);
			});
			stompClient.subscribe('/topic/data2', function(
					DataMessage) {
				showGreeting2(JSON.parse(DataMessage.body).value);
			});
		});
	}

	function disconnect() {
		if (stompClient != null) {
			stompClient.disconnect();
		}
		setConnected(false);
		console.log("Disconnected");
	}

	function sendName() {
		var name = document.getElementById('name').value;
		stompClient.send("/app/hello", {}, JSON.stringify({
			'name' : name
		}));
	}

	function showGreeting(message) {
		//var v = parseFloat(message);
		//$("#fv_0").text(v.toFixed(2));
		
		$("#fv_0").text(message.toFixed(2));
		
		/* var response = document.getElementById('response');
		var p = document.createElement('p');
		p.style.wordWrap = 'break-word';
		p.appendChild(document.createTextNode(message));
		response.appendChild(p); */
		
	}
	
	function showGreeting2(message) {
		//var v = parseFloat(message);
		//$("#fv_0").text(v.toFixed(2));
		
		$("#fv_1").text(message.toFixed(2));
		
		/* var response = document.getElementById('response');
		var p = document.createElement('p');
		p.style.wordWrap = 'break-word';
		p.appendChild(document.createTextNode(message));
		response.appendChild(p); */
		
	}
</script>
</head>
<body>

<!-- websocket test -->
				<div>
					<div>
						<button id="connect" onclick="connect();">Connect</button>
						<button id="disconnect" disabled="disabled"
							onclick="disconnect();">Disconnect</button>
					</div>			
				</div>			
				
	<!-- the main container, there will be only 1, contains all devices -->
	<div class="container">
		<!-- here, are all dynamic, each row with 2 or 3 profiles, each device have unlimited rows -->
		<!-- each time, generate 1 full row with all columns, no matter the number of device are, use 2 for now -->


		<c:set var="profiles" value="${device.profiles}" />
		<c:set var="modv" value="${fn:length(profiles)%2}" />
		<c:set var="divide" value="${fn:length(profiles)/2}" />
		<c:set var="rows" value="${(modv > 0) ? divide+1 : divide}" />
		<c:forEach var="i" begin="0" end="${rows-1}">
			<div class="row clearfix">				
				<!-- loop for each device, 2 profile in a row -->
				<c:forEach var="p" begin="0" end="1">
					<div class="col-md-6 show-grid">
						<!-- render if there is a device -->
						<c:if test="${not empty profiles[i*2+p]}">
					device_profile <c:out value="${i*2+p}" />
							<!-- populate profile content, that is, services -->
							<c:set var="currentp" value="${ profiles[i*2+p]}" />
							<c:forEach var="j" begin="0"
								end="${fn:length(currentp.services) -1 }">
								<div class="row clearfix">
									<div class="col-md-12">
										<!-- need to categorize characteristics, group them, do this in background -->
										<c:set var="currentsvc" value="${currentp.services[j]}" />
										<!-- one row for each text characteristic -->

										<c:if test="${not empty currentsvc.textCharacteristic}">
											<c:forEach items="${currentsvc.textCharacteristic}"
												var="textitem">
												<div class="row clearfix">
													<div class="col-md-12 clearfix showgrid">
														<c:if test="${textitem.readonly}">
															<p>${textitem.text}</p>
														</c:if>

														<c:if test="${not textitem.readonly}">
															Configuration <br />
															<textarea class="form-control" rows="2"></textarea>
															<button type="button" class="btn btn-default">Save</button>
														</c:if>
													</div>
												</div>
											</c:forEach>
										</c:if>

										<c:if test="${not empty currentsvc.csvCharacteristic}">
											<c:set var="svcmod"
												value="${fn:length(currentsvc.csvCharacteristic)%3}" />
											<c:set var="svcdivide"
												value="${fn:length(currentsvc.csvCharacteristic)/3}" />
											<!-- just test with float only, each row of service display 3 float -->
											<c:set var="svcrows"
												value="${(svcmod > 0) ?svcdivide+1 :svcdivide}" />

											<c:forEach var="k" begin="0" end="${svcrows-1}">
												<div class="row clearfix">
													<!-- 3 column should be dynamic too -->
													<c:forEach var="l" begin="0" end="2">
														<c:if
															test="${not empty currentsvc.csvCharacteristic[k*3+l]}">
															<div class="col-md-4 clearfix showgrid">
																Value <br class=""> <span
																	class="badge badge-default">csv</span> <span
																	class="lead pull-right text-primary clearfix"> <c:set
																		var="tokencount"
																		value="${fn:length(currentsvc.csvCharacteristic[k*3+l].csv)}" />
																	<c:forEach var="valueitem" varStatus="status"
																		items="${currentsvc.csvCharacteristic[k*3+l].csv}">
																		<c:out value="${valueitem}" />
																		<c:if test="${tokencount != status.count }">
																			<c:out value="," />
																		</c:if>
																	</c:forEach>
																</span>
															</div>
														</c:if>

													</c:forEach>
												</div>
											</c:forEach>
										</c:if>

										<c:if test="${not empty currentsvc.floatCharacteristic}">
											<c:set var="svcmod"
												value="${fn:length(currentsvc.floatCharacteristic)%3}" />
											<c:set var="svcdivide"
												value="${fn:length(currentsvc.floatCharacteristic)/3}" />
											<!-- just test with float only, each row of service display 3 float -->
											<c:set var="svcrows"
												value="${(svcmod > 0) ?svcdivide+1 :svcdivide}" />
											<%-- <c:forEach var="k" begin="0"
											end="${(fn:length(currentsvc.floatCharacteristic)%3 > 0) ?fn:length(currentsvc.floatCharacteristic)/3 :fn:length(currentsvc.floatCharacteristic)/3 -1}"> --%>
											<c:forEach var="k" begin="0" end="${svcrows-1}">
												<div class="row clearfix">
													<!-- 3 column should be dynamic too -->
													<c:forEach var="l" begin="0" end="2">

														<c:if
															test="${not empty currentsvc.floatCharacteristic[k*3+l]}">
															<div class="col-md-4 clearfix showgrid" id="test${k*3+l}">
																Value <br class=""> <span
																	class="badge badge-default">fl</span> <span
																	class="lead pull-right text-primary clearfix" id="fv_${k*3+l}"><c:out
																		value="${currentsvc.floatCharacteristic[k*3+l].value}" /></span>
															</div>
														</c:if>

													</c:forEach>
												</div>
											</c:forEach>
										</c:if>

										<c:if test="${not empty currentsvc.booleanCharacteristic}">
											<c:forEach var="br" begin="0"
												end="${(fn:length(currentsvc.booleanCharacteristic)%4 > 0) ?fn:length(currentsvc.booleanCharacteristic)/4 :fn:length(currentsvc.booleanCharacteristic)/4 -1}">
												<div class="row clearfix">
													<!-- 3 column should be dynamic too -->
													<c:forEach var="bc" begin="0" end="3">
														<c:if
															test="${not empty currentsvc.booleanCharacteristic[br*4+bc]}">
															<div class="col-md-3 clearfix showgrid">
																Toggle <br class=""> <input type="checkbox"
																	<c:if test="${currentsvc.booleanCharacteristic[br*4+bc].status}">
																	checked
																</c:if>
																	data-toggle="toggle" data-width="48" data-height="28">
															</div>
														</c:if>
													</c:forEach>
												</div>
											</c:forEach>
										</c:if>

										<c:if test="${not empty currentsvc.stateCharacteristic}">
											<c:forEach items="${currentsvc.stateCharacteristic}"
												var="stateitem">
												<div class="row clearfix">
													<div class="col-md-12 clearfix showgrid">
														<c:if test="${stateitem.readonly}">
															<div class="row">
																<c:forEach var="ss" begin="0"
																	end="${fn:length(stateitem.state)-1}">
																	<div class="col-xs-3 text-center">
																		<c:out value="${stateitem.state[ss].key }" />
																	</div>
																</c:forEach>
															</div>
															<div class="row" style="padding-top: 1em;">
																<c:forEach var="ss" begin="0"
																	end="${fn:length(stateitem.state)-1}">
																	<div class="col-xs-3 text-center">
																		<c:if test="${stateitem.state[ss].value}">
																			<i class="fa fa-circle fa-circle--active"></i>
																		</c:if>
																		<c:if test="${!stateitem.state[ss].value}">
																			<i class="fa fa-circle"></i>
																		</c:if>
																	</div>
																</c:forEach>
															</div>
														</c:if>
													</div>
												</div>
											</c:forEach>
										</c:if>
									</div>
								</div>
							</c:forEach>
						</c:if>
					</div>
				</c:forEach>

			</div>
		</c:forEach>
	</div>
</body>
</html>