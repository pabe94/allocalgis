<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html xhtml="true" lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>AL LocalGIS Guia Urbana_Login</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon" />
<link href="${pageContext.request.contextPath}/css/staticStyles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
<!--
function MM_preloadImages(  ) { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function checkReturn(e){
    var evt = e || window.event;
    if(!evt) 
        return;
    var key = 0;
    
    if (evt.keyCode) { key = evt.keyCode; } 
    else if (typeof(evt.which)!= 'undefined') { key = evt.which; } 
    if( key == 13 )
    {
        document.forms[0].submit();
    }
}

//-->
</script>
</head>

    <body onload="document.forms[0].username.focus();">
        <!-- Recuadro superior de la pagina -->
        <div id="wrap">
            <div id="top" class="top">
                <img src="${pageContext.request.contextPath}/img/top_left.gif" alt="Esquina superior izquierda" class="esquina_sup_izq" />
                <img src="${pageContext.request.contextPath}/img/top_right.gif" alt="Esquina superior derecha" class="esquina_sup_der" />
            </div>
            <div id="content" class="content">
                <div id="boxcontrol" class="boxcontrol">

                    <!-- Contenido del recuadro superior -->
                    <div id="bannerGeneral"></div>
                                        
                        <html:form action="/${requestScope.configurationLocalgisWeb}/login.do">
                    <div id="titlePage"> 
                        <h1>Inicio de Sesi�n</h1>
                        
                    </div>
                    <div id="contentPage<logic:equal name="dnie.authactive" value="true">DNIe</logic:equal>">
                            <table id="centeredTableLoginWithMunicipio" border="0">
                                <logic:equal name="dnie.authactive" value="true">
                                <tr>
                                    <td colspan="4" valign="middle" align="center" style="padding-right: 0px;">
                                       <img class="imageButton" src="${pageContext.request.contextPath}/img/dnie/btn_dnie.gif" alt="DNIe" style="vertical-align:bottom" onclick="document.location.href='../dnie/';" />
                                    </td>                                    
                                </tr>
                                <tr>
                                    <td colspan="4" valign="middle" align="center" style="padding-right: 0px;height:15px;"></td>                                    
                                </tr>
                                </logic:equal>                                
                                <tr>
                                    <td rowspan="2" valign="middle" style="padding-right: 0px;">
                                        <img src="${pageContext.request.contextPath}/img/user.gif" alt="User" />
                                    </td>
                                    <td align="center"  style="padding-left: 6px;">
                                        Nombre de Usuario
                                    </td>
                                    <td rowspan="2" valign="middle" style="padding-right: 0px;">
                                        <img src="${pageContext.request.contextPath}/img/key.gif" alt="Icono llave" />                                        
                                    </td>
                                    <td align="center" style="padding-left: 2px;">
                                        Contrase�a
                                    </td>
                                </tr>
                                <tr style="padding-top: 6px;">
                                    <td align="center" valign="middle" style="padding-left: 6px;">
                                        <input type="text" class="inputTextField" name="username"/>
                                    </td>
                                    <td align="center" valign="middle" style="padding-left: 2px;">
                                        <input type="password" class="inputTextField" name="password" onkeypress="checkReturn(event);" />
                                    </td>
                                </tr>                             
                                <tr>
                                    <td valign="middle" colspan="4" style="padding-top: 4px;">
                                        <img class="imageButton" src="${pageContext.request.contextPath}/img/btn_aceptar.gif" alt="Aceptar" style="vertical-align:bottom" onclick="document.forms[0].submit();" />
                                    </td>
                                </tr>
                                <bean:define id="error" value="${param.error}"/>
                                <logic:notEmpty name="error">
                                <tr>
                                    <td valign="middle" colspan="4" style="padding-top: 4px;">
                                        <span class="errorMessage">Usuario o contrase�a invalidos</span>
                                    </td>
                                </tr>    
                                </logic:notEmpty>
                            </table>                        
                    </div>
                    </html:form>
                    <!--  Fin del contenido de la pagina -->
                </div>
            </div>
            <div id="bottom" class="bottom">
                <img src="${pageContext.request.contextPath}/img/btm_left.gif" alt="Esquina inferior izquierda" class="esquina_inf_izq" />
                <img src="${pageContext.request.contextPath}/img/btm_right.gif" alt="Esquina inferior derecha" class="esquina_inf_der" />
            </div>
        </div>
        <!-- Fin del recuadro superior de la pagina -->

        <!-- Recuadro inferior de la pagina -->
        <div id="wrap">
            <div id="top" class="top">
                <img src="${pageContext.request.contextPath}/img/top_left.gif" alt="Esquina superior izquierda" class="esquina_sup_izq" />
                <img src="${pageContext.request.contextPath}/img/top_right.gif" alt="Esquina superior derecha" class="esquina_sup_der" />
            </div>
            <div id="content" class="content">
                <div id="boxcontrol" class="boxcontrol">
                    <!-- Contenido del recuadro -->
                    <div id="footer" class="footer"></div>
                    <!--  Fin del contenido del recuadro inferior -->
                </div>
            </div>
            <div id="bottom" class="bottom">
                <img src="${pageContext.request.contextPath}/img/btm_left.gif" alt="Esquina inferior izquierda" class="esquina_inf_izq" />
                <img src="${pageContext.request.contextPath}/img/btm_right.gif" alt="Esquina inferior derecha" class="esquina_inf_der" />
            </div>
        </div>
        <!-- Fin del recuadro inferior de la pagina -->

    </body>
</html:html>