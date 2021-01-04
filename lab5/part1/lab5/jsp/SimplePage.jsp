<jsp:useBean id="theBean" class="com.brainysoftware.CalculatorBean1"/> 
<jsp:setProperty name="theBean" property="memory" param="memory"/> 
The value of memory is <jsp:getProperty name="theBean" property="memory"/>
