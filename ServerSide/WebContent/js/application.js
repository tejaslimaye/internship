/*!
* JavaScript for Bootstrap's docs (https://getbootstrap.com)
* Copyright 2011-2017 The Bootstrap Authors
* Copyright 2011-2017 Twitter, Inc.
* Licensed under the Creative Commons Attribution 3.0 Unported License. For
* details, see https://creativecommons.org/licenses/by/3.0/.
*/(function($){'use strict'
function getCookie(cname){var name=cname+'=';var decodedCookie=decodeURIComponent(document.cookie);var ca=decodedCookie.split(';');for(var i=0;i<ca.length;i++){var c=ca[i];while(c.charAt(0)==' '){c=c.substring(1);}
if(c.indexOf(name)==0){return c.substring(name.length,c.length);}}
return '';}
function setCookie(cname,cvalue,exdays){var d=new Date();d.setTime(d.getTime()+(exdays*24*60*60*1000));var expires='expires='+d.toUTCString();document.cookie=cname+'='+cvalue+';'+expires+';path=/';}
function checkIfEu(url){var eu=['AL','AD','AT','AZ','BY','BE','BA','BG','HR','CY','CZ','DK','EE','FI','FR','GE','DE','GR','HU','IS','IE','IT','KZ','XK','LV','LI','LT','LU','MK','MT','MD','MC','ME','NL','NO','PL','PT','RO','RU','SM','RS','SK','SI','ES','SE','CH','TR','UA','GB','VA']
$.getJSON('https://pro.ip-api.com/json/?key=EEKS6bLi6D91G1p',function(data){var countryCode=data.countryCode;if(eu.indexOf(countryCode)>0&&getCookie('cookiesAccepted')!='true'){var cookiesStatementContainer=document.createElement('div');cookiesStatementContainer.setAttribute('class','fixed-bottom p-3');cookiesStatementContainer.setAttribute('id','cookies-statement');var cookiesStatement=document.createElement('div');cookiesStatement.innerHTML='By continuing to use the site, you agree to the use of cookies. <a href="'+url+'">Learn More</a><button type="button" id="accept-cookies" class="btn btn-sm btn-outline-success ml-3">Accept</button>';cookiesStatement.setAttribute('class','alert alert-primary mb-2 text-center d-inline');cookiesStatementContainer.appendChild(cookiesStatement);document.body.appendChild(cookiesStatementContainer);}});}
$(function(){checkIfEu('https://coreui.io/about/legal/cookies/');$('body').on('click','button#accept-cookies',function(){setCookie('cookiesAccepted',true,365);$('#cookies-statement').hide();})
$('.tooltip-demo').tooltip({selector:'[data-toggle="tooltip"]',container:'body'})
$('[data-toggle="popover"]').popover()
$('.tooltip-test').tooltip()
$('.popover-test').popover()
$('.bd-example-indeterminate [type="checkbox"]').prop('indeterminate',true)
$('.bd-content [href="#"]').click(function(e){e.preventDefault()})
$('#exampleModal').on('show.bs.modal',function(event){var $button=$(event.relatedTarget)
var recipient=$button.data('whatever')
var $modal=$(this)
$modal.find('.modal-title').text('New message to '+recipient)
$modal.find('.modal-body input').val(recipient)})
$('.bd-toggle-animated-progress').on('click',function(){$(this).siblings('.progress').find('.progress-bar-striped').toggleClass('progress-bar-animated')})
$('.highlight').each(function(){var btnHtml='<div class="bd-clipboard"><button class="btn-clipboard" title="Copy to clipboard">Copy</button></div>'
$(this).before(btnHtml)
$('.btn-clipboard').tooltip().on('mouseleave',function(){$(this).tooltip('hide')})})
var clipboard=new Clipboard('.btn-clipboard',{target:function(trigger){return trigger.parentNode.nextElementSibling}})
clipboard.on('success',function(e){$(e.trigger).attr('title','Copied!').tooltip('_fixTitle').tooltip('show').attr('title','Copy to clipboard').tooltip('_fixTitle')
e.clearSelection()})
clipboard.on('error',function(e){var modifierKey=/Mac/i.test(navigator.userAgent)?'\u2318':'Ctrl-'
var fallbackMsg='Press '+modifierKey+'C to copy'
$(e.trigger).attr('title',fallbackMsg).tooltip('_fixTitle').tooltip('show').attr('title','Copy to clipboard').tooltip('_fixTitle')})
anchors.options={icon:'#'}
anchors.add('.bd-content > h2, .bd-content > h3, .bd-content > h4, .bd-content > h5')
$('.bd-content > h2, .bd-content > h3, .bd-content > h4, .bd-content > h5').wrapInner('<div></div>')})}(jQuery))