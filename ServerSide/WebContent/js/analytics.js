'use strict'
function queryString(){var query_string={};var query=window.location.search.substring(1);var vars=query.split('&');for(var i=0;i<vars.length;i++){var pair=vars[i].split('=');if(typeof query_string[pair[0]]==='undefined'){query_string[pair[0]]=decodeURIComponent(pair[1]);}else if(typeof query_string[pair[0]]==='string'){var arr=[query_string[pair[0]],decodeURIComponent(pair[1])];query_string[pair[0]]=arr;}else{query_string[pair[0]].push(decodeURIComponent(pair[1]));}}
return query_string;};function checkCookie(){var username=getCookie('username');if(username!=''){}else{username=prompt('Please enter your name:','');if(username!=''&&username!=null){setCookie('username',username,365);}}}
function getCookie(cname){var name=cname+'=';var decodedCookie=decodeURIComponent(document.cookie);var ca=decodedCookie.split(';');for(var i=0;i<ca.length;i++){var c=ca[i];while(c.charAt(0)==' '){c=c.substring(1);}
if(c.indexOf(name)==0){return c.substring(name.length,c.length);}}
return '';}
function setCookie(cname,cvalue,exdays){var d=new Date();d.setTime(d.getTime()+(exdays*24*60*60*1000));var expires='expires='+d.toUTCString();document.cookie=cname+'='+cvalue+';'+expires+';path=/';}
function setVisits(){var firstVisit=getCookie('FirstVisit');var lastVisit=getCookie('LastVisit');var visits=getCookie('Visits');var date=new Date();var newDate=date.getTime();if(firstVisit==''){setCookie('FirstVisit',newDate,365);setCookie('Visits',1,365);}
var hours=((newDate-lastVisit)/(1000*60*60)).toFixed(1);if(hours>12){visits=visits+1;setCookie('Visits',visits,365);}
setCookie('LastVisit',newDate,365);}
$(function(){setVisits();});