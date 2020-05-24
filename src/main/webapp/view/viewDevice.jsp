<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="template/header.jsp"/>
<script type="text/javascript" charset="utf8">
    <%@include file='../js/Highcharts/code/highstock.js' %>
</script>
<script type="text/javascript" charset="utf8">
    <%@include file='../js/Highcharts/code/modules/data.js' %>
</script>
<script type="text/javascript" charset="utf8">
    <%@include file='../js/Highcharts/code/modules/exporting.js' %>
</script>
<script type="text/javascript" charset="utf8">
    <%@include file='../js/Highcharts/code/modules/export-data.js' %>
</script>
<div class="p-2 d-flex">
    <div class="w-25 px-1" >
        <h2>Устройство</h2>
        <form method="post" class="py-3" >
            <div class="form-row">
                <div class="form-group col-md-11">
                    <label for="nameDevice">Название устройства</label>
                    <input type="text" class="form-control" id="nameDevice" name="nameDevice" value=${device.nameDevice}>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-4">
                    <label for="idDevice">Id</label>
                    <input readonly type="text" class="form-control" id="idDevice" name="idDevice" value=${device.idDevice}>
                </div>
                <div class="form-group col-md-8">
                    <label for="controller">Контроллер</label>
                    <select id="controller" class="form-control" name="controller" >
                        <c:forEach var="controller" items="${controllers}">
                            <option <c:if test="${device.controller == controller.idController}">selected</c:if> value="${controller.idController}">${controller.nameController}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-8">
                    <label for="typeDevice">Тип устройства</label>
                    <select id="typeDevice" class="form-control" name="typeDevice" >
                        <c:forEach var="type" items="${types}">
                            <option <c:if test="${device.typeDevice == type.idType}">selected</c:if> value="${type.idType}">${type.nameType}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-8">
                    <label for="workStatus">Статус</label>
                    <select id="workStatus" class="form-control" name="workStatus">
                        <c:forEach var="status" items="${statuses}">
                            <option <c:if test="${device.workStatus == status.idStatus}">selected</c:if> value="${status.idStatus}">${status.nameStatus}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-11">
                    <label for="description">Описание</label>
                    <textarea id="description" name="description" class="form-control">${device.description}</textarea>
                </div>
            </div>
            <button type="submit" class="btn btn-primary" formaction="./updDevice">Обновить</button>
            <button type="submit" class="btn btn-danger m-1" formaction="./delDevice">Удалить</button>
            <button type="reset" class="btn btn-warning">Отменить</button>
        </form>
        <c:if test="${isDeviceupd == true}">
            <div class="alert alert-success alert-dismissible fade show col-md-12" role="alert">
                <strong>Устройство обновлено</strong>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <c:if test="${isDeviceupd == false}">
            <div class="alert alert-danger alert-dismissible fade show col-md-12" role="alert">
                <strong>Устройство не обновлено</strong> Проверьте вводимые значения
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
    </div>
    <div class="w-75">
        <div id="container" style="height: 400px; min-width: 310px"></div>
        <script type="text/javascript" charset="utf8">
            <%@include file='../js/Device.js' %>
        </script>
</div>
</div>
</body>
</html>