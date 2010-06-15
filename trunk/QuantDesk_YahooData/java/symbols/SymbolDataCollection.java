


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <link rel="icon" type="image/vnd.microsoft.icon" href="http://www.gstatic.com/codesite/ph/images/phosting.ico">
 
 <script type="text/javascript">
 
 
 
 var codesite_token = "3d9c2eb32fa665c48bb7a870307cd8bd";
 
 
 var logged_in_user_email = "aleksandr.zelenkov@gmail.com";
 
 
 var relative_base_url = "";
 
 </script>
 
 
 <title>SymbolDataCollection.java - 
 quantdesk -
 
 Project Hosting on Google Code</title>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
 
 <link type="text/css" rel="stylesheet" href="http://www.gstatic.com/codesite/ph/13637207143931721394/css/ph_core.css">
 
 <link type="text/css" rel="stylesheet" href="http://www.gstatic.com/codesite/ph/13637207143931721394/css/ph_detail.css" >
 
 
 <link type="text/css" rel="stylesheet" href="http://www.gstatic.com/codesite/ph/13637207143931721394/css/d_sb_20080522.css" >
 
 
 
<!--[if IE]>
 <link type="text/css" rel="stylesheet" href="http://www.gstatic.com/codesite/ph/13637207143931721394/css/d_ie.css" >
<![endif]-->
 <style type="text/css">
 .menuIcon.off { background: no-repeat url(http://www.gstatic.com/codesite/ph/images/dropdown_sprite.gif) 0 -42px }
 .menuIcon.on { background: no-repeat url(http://www.gstatic.com/codesite/ph/images/dropdown_sprite.gif) 0 -28px }
 .menuIcon.down { background: no-repeat url(http://www.gstatic.com/codesite/ph/images/dropdown_sprite.gif) 0 0; }
 </style>
</head>
<body class="t4">
 <script type="text/javascript">
 var _gaq = _gaq || [];
 _gaq.push(
 ['siteTracker._setAccount', 'UA-18071-1'],
 ['siteTracker._trackPageview']);
 
 _gaq.push(
 ['projectTracker._setAccount', 'UA-16145685-2'],
 ['projectTracker._trackPageview']);
 
 (function() {
 var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
 ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
 (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(ga);
 })();
 </script>
 <div id="gaia">
 
 <span>
 
 
 <b>aleksandr.zelenkov@gmail.com</b>
 
 
 | <a href="/u/aleksandr.zelenkov/" id="projects-dropdown" onclick="return false;"
 ><u>My favorites</u> <small>&#9660;</small></a>
 | <a href="/u/aleksandr.zelenkov/" onclick="_CS_click('/gb/ph/profile');" 
 title="Profile, Updates, and Settings"
 ><u>Profile</u></a>
 | <a href="https://www.google.com/accounts/Logout?continue=http%3A%2F%2Fcode.google.com%2Fp%2Fquantdesk%2Fsource%2Fbrowse%2FQuantDesk_YahooData%2Fjava%2Fcom%2Fwinance%2Foptimizer%2Fsymbols%2FSymbolDataCollection.java%3Fspec%3Dsvn22%26r%3D22" 
 onclick="_CS_click('/gb/ph/signout');"
 ><u>Sign out</u></a>
 
 </span>

 </div>
 <div class="gbh" style="left: 0pt;"></div>
 <div class="gbh" style="right: 0pt;"></div>
 
 
 <div style="height: 1px"></div>
<!--[if IE 6]>
<div style="text-align:center;">
Support browsers that contribute to open source, try <a href="http://www.firefox.com">Firefox</a> or <a href="http://www.google.com/chrome">Google Chrome</a>.
</div>
<![endif]-->



 <table style="padding:0px; margin: 20px 0px 0px 0px; width:100%" cellpadding="0" cellspacing="0">
 <tr style="height: 58px;">
 
 <td style="width: 55px; text-align:center;">
 <a href="/p/quantdesk/">
 
 <img src="http://www.gstatic.com/codesite/ph/images/defaultlogo.png" alt="Logo">
 
 </a>
 </td>
 
 <td style="padding-left: 0.5em">
 
 <div id="pname" style="margin: 0px 0px -3px 0px">
 <a href="/p/quantdesk/" style="text-decoration:none; color:#000">quantdesk</a>
 
 </div>
 <div id="psum">
 <i><a id="project_summary_link" href="/p/quantdesk/" style="text-decoration:none; color:#000">Free Software for Quantitative Analysis of Stock and Financial Market Investments</a></i>
 </div>
 
 </td>
 <td style="white-space:nowrap;text-align:right">
 
 <form action="/hosting/search">
 <input size="30" name="q" value="">
 <input type="submit" name="projectsearch" value="Search projects" >
 </form>
 
 </tr>
 </table>


 
<table id="mt" cellspacing="0" cellpadding="0" width="100%" border="0">
 <tr>
 <th onclick="if (!cancelBubble) _go('/p/quantdesk/');">
 <div class="tab inactive">
 <div class="round4"></div>
 <div class="round2"></div>
 <div class="round1"></div>
 <div class="box-inner">
 <a onclick="cancelBubble=true;" href="/p/quantdesk/">Project&nbsp;Home</a>
 </div>
 </div>
 </th><td>&nbsp;&nbsp;</td>
 
 
 
 
 <th onclick="if (!cancelBubble) _go('/p/quantdesk/downloads/list');">
 <div class="tab inactive">
 <div class="round4"></div>
 <div class="round2"></div>
 <div class="round1"></div>
 <div class="box-inner">
 <a onclick="cancelBubble=true;" href="/p/quantdesk/downloads/list">Downloads</a>
 </div>
 </div>
 </th><td>&nbsp;&nbsp;</td>
 
 
 
 
 
 <th onclick="if (!cancelBubble) _go('/p/quantdesk/w/list');">
 <div class="tab inactive">
 <div class="round4"></div>
 <div class="round2"></div>
 <div class="round1"></div>
 <div class="box-inner">
 <a onclick="cancelBubble=true;" href="/p/quantdesk/w/list">Wiki</a>
 </div>
 </div>
 </th><td>&nbsp;&nbsp;</td>
 
 
 
 
 
 <th onclick="if (!cancelBubble) _go('/p/quantdesk/issues/list');">
 <div class="tab inactive">
 <div class="round4"></div>
 <div class="round2"></div>
 <div class="round1"></div>
 <div class="box-inner">
 <a onclick="cancelBubble=true;" href="/p/quantdesk/issues/list">Issues</a>
 </div>
 </div>
 </th><td>&nbsp;&nbsp;</td>
 
 
 
 
 
 <th onclick="if (!cancelBubble) _go('/p/quantdesk/source/checkout');">
 <div class="tab active">
 <div class="round4"></div>
 <div class="round2"></div>
 <div class="round1"></div>
 <div class="box-inner">
 <a onclick="cancelBubble=true;" href="/p/quantdesk/source/checkout">Source</a>
 </div>
 </div>
 </th><td>&nbsp;&nbsp;</td>
 
 
 <td width="100%">&nbsp;</td>
 </tr>
</table>
<table cellspacing="0" cellpadding="0" width="100%" align="center" border="0" class="st">
 <tr>
 
 
 
 
 
 
 <td>
 <div class="st2">
 <div class="isf">
 
 
 
 <span class="inst1"><a href="/p/quantdesk/source/checkout">Checkout</a></span> |
 <span class="inst2"><a href="/p/quantdesk/source/browse/">Browse</a></span> |
 <span class="inst3"><a href="/p/quantdesk/source/list">Changes</a></span> |
 
 <form action="http://www.google.com/codesearch" method="get" style="display:inline"
 onsubmit="document.getElementById('codesearchq').value = document.getElementById('origq').value + ' package:http://quantdesk\\.googlecode\\.com'">
 <input type="hidden" name="q" id="codesearchq" value="">
 <input maxlength="2048" size="38" id="origq" name="origq" value="" title="Google Code Search" style="font-size:92%">&nbsp;<input type="submit" value="Search Trunk" name="btnG" style="font-size:92%">
 
 
 
 </form>
 </div>
</div>

 </td>
 
 
 
 <td height="4" align="right" valign="top" class="bevel-right">
 <div class="round4"></div>
 <div class="round2"></div>
 <div class="round1"></div>
 </td>
 </tr>
</table>
<script type="text/javascript">
 var cancelBubble = false;
 function _go(url) { document.location = url; }
</script>


<div id="maincol"
 
>

 
<!-- IE -->




<div class="expand">


<style type="text/css">
 #file_flipper { display: inline; float: right; white-space: nowrap; }
 #file_flipper.hidden { display: none; }
 #file_flipper .pagelink { color: #0000CC; text-decoration: underline; }
 #file_flipper #visiblefiles { padding-left: 0.5em; padding-right: 0.5em; }
</style>
<div id="nav_and_rev" class="heading">
 <div class="round4"></div>
 <div class="round2"></div>
 <div class="round1"></div>
 <div class="box-inner" id="bub">
 <div class="bub-top">
 <div class="pagination" style="margin-left: 2em">
 <table cellpadding="0" cellspacing="0" class="flipper">
 <tbody>
 <tr>
 
 <td><b>r22</b></td>
 
 </tr>
 </tbody>
 </table>
 </div>
 
 <div class="" style="vertical-align: top">
 <div class="src_crumbs src_nav">
 <strong class="src_nav">Source path:&nbsp;</strong>
 <span id="crumb_root">
 
 <a href="/p/quantdesk/source/browse/?r=22">svn</a>/&nbsp;</span>
 <span id="crumb_links" class="ifClosed"><a href="/p/quantdesk/source/browse/QuantDesk_YahooData/?r=22">QuantDesk_YahooData</a><span class="sp">/&nbsp;</span><a href="/p/quantdesk/source/browse/QuantDesk_YahooData/java/?r=22">java</a><span class="sp">/&nbsp;</span><a href="/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/?r=22">com</a><span class="sp">/&nbsp;</span><a href="/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/?r=22">winance</a><span class="sp">/&nbsp;</span><a href="/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/?r=22">optimizer</a><span class="sp">/&nbsp;</span><a href="/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/?r=22">symbols</a><span class="sp">/&nbsp;</span>SymbolDataCollection.java</span>
 
 
 </div>
 
 </div>
 <div style="clear:both"></div>
 </div>
 </div>
</div>

<style type="text/css">
 
  tr.inline_comment {
 background: #fff;
 vertical-align: top;
 }
 div.draft, div.published {
 padding: .3em;
 border: 1px solid #999; 
 margin-bottom: .1em;
 font-family: arial, sans-serif;
 max-width: 60em;
 }
 div.draft {
 background: #ffa;
 } 
 div.published {
 background: #e5ecf9;
 }
 div.published .body, div.draft .body {
 padding: .5em .1em .1em .1em;
 max-width: 60em;
 white-space: pre-wrap;
 white-space: -moz-pre-wrap;
 white-space: -pre-wrap;
 white-space: -o-pre-wrap;
 word-wrap: break-word;
 }
 div.draft .actions {
 margin-left: 1em;
 font-size: 90%;
 }
 div.draft form {
 padding: .5em .5em .5em 0;
 }
 div.draft textarea, div.published textarea {
 width: 95%;
 height: 10em;
 font-family: arial, sans-serif;
 margin-bottom: .5em;
 }


 
 .nocursor, .nocursor td, .cursor_hidden, .cursor_hidden td {
 background-color: white;
 height: 2px;
 }
 .cursor, .cursor td {
 background-color: darkblue;
 height: 2px;
 display: '';
 }

</style>
<div class="fc">
 
 
 
<style type="text/css">
.undermouse span { 
 background-image: url(http://www.gstatic.com/codesite/ph/images/comments.gif); }
</style>
<table class="opened" id="review_comment_area" 
><tr>
<td id="nums">
<pre><table width="100%"><tr class="nocursor"><td></td></tr></table></pre>

<pre><table width="100%" id="nums_table_0"><tr id="gr_svn22_1"

><td id="1"><a href="#1">1</a></td></tr
><tr id="gr_svn22_2"

><td id="2"><a href="#2">2</a></td></tr
><tr id="gr_svn22_3"

><td id="3"><a href="#3">3</a></td></tr
><tr id="gr_svn22_4"

><td id="4"><a href="#4">4</a></td></tr
><tr id="gr_svn22_5"

><td id="5"><a href="#5">5</a></td></tr
><tr id="gr_svn22_6"

><td id="6"><a href="#6">6</a></td></tr
><tr id="gr_svn22_7"

><td id="7"><a href="#7">7</a></td></tr
><tr id="gr_svn22_8"

><td id="8"><a href="#8">8</a></td></tr
><tr id="gr_svn22_9"

><td id="9"><a href="#9">9</a></td></tr
><tr id="gr_svn22_10"

><td id="10"><a href="#10">10</a></td></tr
><tr id="gr_svn22_11"

><td id="11"><a href="#11">11</a></td></tr
><tr id="gr_svn22_12"

><td id="12"><a href="#12">12</a></td></tr
><tr id="gr_svn22_13"

><td id="13"><a href="#13">13</a></td></tr
><tr id="gr_svn22_14"

><td id="14"><a href="#14">14</a></td></tr
><tr id="gr_svn22_15"

><td id="15"><a href="#15">15</a></td></tr
><tr id="gr_svn22_16"

><td id="16"><a href="#16">16</a></td></tr
><tr id="gr_svn22_17"

><td id="17"><a href="#17">17</a></td></tr
><tr id="gr_svn22_18"

><td id="18"><a href="#18">18</a></td></tr
><tr id="gr_svn22_19"

><td id="19"><a href="#19">19</a></td></tr
><tr id="gr_svn22_20"

><td id="20"><a href="#20">20</a></td></tr
><tr id="gr_svn22_21"

><td id="21"><a href="#21">21</a></td></tr
><tr id="gr_svn22_22"

><td id="22"><a href="#22">22</a></td></tr
><tr id="gr_svn22_23"

><td id="23"><a href="#23">23</a></td></tr
><tr id="gr_svn22_24"

><td id="24"><a href="#24">24</a></td></tr
><tr id="gr_svn22_25"

><td id="25"><a href="#25">25</a></td></tr
><tr id="gr_svn22_26"

><td id="26"><a href="#26">26</a></td></tr
><tr id="gr_svn22_27"

><td id="27"><a href="#27">27</a></td></tr
><tr id="gr_svn22_28"

><td id="28"><a href="#28">28</a></td></tr
><tr id="gr_svn22_29"

><td id="29"><a href="#29">29</a></td></tr
><tr id="gr_svn22_30"

><td id="30"><a href="#30">30</a></td></tr
><tr id="gr_svn22_31"

><td id="31"><a href="#31">31</a></td></tr
><tr id="gr_svn22_32"

><td id="32"><a href="#32">32</a></td></tr
><tr id="gr_svn22_33"

><td id="33"><a href="#33">33</a></td></tr
><tr id="gr_svn22_34"

><td id="34"><a href="#34">34</a></td></tr
><tr id="gr_svn22_35"

><td id="35"><a href="#35">35</a></td></tr
><tr id="gr_svn22_36"

><td id="36"><a href="#36">36</a></td></tr
><tr id="gr_svn22_37"

><td id="37"><a href="#37">37</a></td></tr
><tr id="gr_svn22_38"

><td id="38"><a href="#38">38</a></td></tr
><tr id="gr_svn22_39"

><td id="39"><a href="#39">39</a></td></tr
><tr id="gr_svn22_40"

><td id="40"><a href="#40">40</a></td></tr
><tr id="gr_svn22_41"

><td id="41"><a href="#41">41</a></td></tr
><tr id="gr_svn22_42"

><td id="42"><a href="#42">42</a></td></tr
><tr id="gr_svn22_43"

><td id="43"><a href="#43">43</a></td></tr
><tr id="gr_svn22_44"

><td id="44"><a href="#44">44</a></td></tr
><tr id="gr_svn22_45"

><td id="45"><a href="#45">45</a></td></tr
><tr id="gr_svn22_46"

><td id="46"><a href="#46">46</a></td></tr
><tr id="gr_svn22_47"

><td id="47"><a href="#47">47</a></td></tr
><tr id="gr_svn22_48"

><td id="48"><a href="#48">48</a></td></tr
><tr id="gr_svn22_49"

><td id="49"><a href="#49">49</a></td></tr
><tr id="gr_svn22_50"

><td id="50"><a href="#50">50</a></td></tr
><tr id="gr_svn22_51"

><td id="51"><a href="#51">51</a></td></tr
><tr id="gr_svn22_52"

><td id="52"><a href="#52">52</a></td></tr
><tr id="gr_svn22_53"

><td id="53"><a href="#53">53</a></td></tr
><tr id="gr_svn22_54"

><td id="54"><a href="#54">54</a></td></tr
><tr id="gr_svn22_55"

><td id="55"><a href="#55">55</a></td></tr
><tr id="gr_svn22_56"

><td id="56"><a href="#56">56</a></td></tr
><tr id="gr_svn22_57"

><td id="57"><a href="#57">57</a></td></tr
><tr id="gr_svn22_58"

><td id="58"><a href="#58">58</a></td></tr
><tr id="gr_svn22_59"

><td id="59"><a href="#59">59</a></td></tr
><tr id="gr_svn22_60"

><td id="60"><a href="#60">60</a></td></tr
><tr id="gr_svn22_61"

><td id="61"><a href="#61">61</a></td></tr
><tr id="gr_svn22_62"

><td id="62"><a href="#62">62</a></td></tr
><tr id="gr_svn22_63"

><td id="63"><a href="#63">63</a></td></tr
><tr id="gr_svn22_64"

><td id="64"><a href="#64">64</a></td></tr
><tr id="gr_svn22_65"

><td id="65"><a href="#65">65</a></td></tr
><tr id="gr_svn22_66"

><td id="66"><a href="#66">66</a></td></tr
><tr id="gr_svn22_67"

><td id="67"><a href="#67">67</a></td></tr
><tr id="gr_svn22_68"

><td id="68"><a href="#68">68</a></td></tr
><tr id="gr_svn22_69"

><td id="69"><a href="#69">69</a></td></tr
><tr id="gr_svn22_70"

><td id="70"><a href="#70">70</a></td></tr
><tr id="gr_svn22_71"

><td id="71"><a href="#71">71</a></td></tr
><tr id="gr_svn22_72"

><td id="72"><a href="#72">72</a></td></tr
><tr id="gr_svn22_73"

><td id="73"><a href="#73">73</a></td></tr
><tr id="gr_svn22_74"

><td id="74"><a href="#74">74</a></td></tr
><tr id="gr_svn22_75"

><td id="75"><a href="#75">75</a></td></tr
><tr id="gr_svn22_76"

><td id="76"><a href="#76">76</a></td></tr
><tr id="gr_svn22_77"

><td id="77"><a href="#77">77</a></td></tr
><tr id="gr_svn22_78"

><td id="78"><a href="#78">78</a></td></tr
><tr id="gr_svn22_79"

><td id="79"><a href="#79">79</a></td></tr
><tr id="gr_svn22_80"

><td id="80"><a href="#80">80</a></td></tr
><tr id="gr_svn22_81"

><td id="81"><a href="#81">81</a></td></tr
><tr id="gr_svn22_82"

><td id="82"><a href="#82">82</a></td></tr
><tr id="gr_svn22_83"

><td id="83"><a href="#83">83</a></td></tr
><tr id="gr_svn22_84"

><td id="84"><a href="#84">84</a></td></tr
><tr id="gr_svn22_85"

><td id="85"><a href="#85">85</a></td></tr
><tr id="gr_svn22_86"

><td id="86"><a href="#86">86</a></td></tr
><tr id="gr_svn22_87"

><td id="87"><a href="#87">87</a></td></tr
><tr id="gr_svn22_88"

><td id="88"><a href="#88">88</a></td></tr
><tr id="gr_svn22_89"

><td id="89"><a href="#89">89</a></td></tr
><tr id="gr_svn22_90"

><td id="90"><a href="#90">90</a></td></tr
><tr id="gr_svn22_91"

><td id="91"><a href="#91">91</a></td></tr
><tr id="gr_svn22_92"

><td id="92"><a href="#92">92</a></td></tr
><tr id="gr_svn22_93"

><td id="93"><a href="#93">93</a></td></tr
><tr id="gr_svn22_94"

><td id="94"><a href="#94">94</a></td></tr
><tr id="gr_svn22_95"

><td id="95"><a href="#95">95</a></td></tr
><tr id="gr_svn22_96"

><td id="96"><a href="#96">96</a></td></tr
><tr id="gr_svn22_97"

><td id="97"><a href="#97">97</a></td></tr
><tr id="gr_svn22_98"

><td id="98"><a href="#98">98</a></td></tr
><tr id="gr_svn22_99"

><td id="99"><a href="#99">99</a></td></tr
><tr id="gr_svn22_100"

><td id="100"><a href="#100">100</a></td></tr
><tr id="gr_svn22_101"

><td id="101"><a href="#101">101</a></td></tr
><tr id="gr_svn22_102"

><td id="102"><a href="#102">102</a></td></tr
><tr id="gr_svn22_103"

><td id="103"><a href="#103">103</a></td></tr
><tr id="gr_svn22_104"

><td id="104"><a href="#104">104</a></td></tr
><tr id="gr_svn22_105"

><td id="105"><a href="#105">105</a></td></tr
><tr id="gr_svn22_106"

><td id="106"><a href="#106">106</a></td></tr
><tr id="gr_svn22_107"

><td id="107"><a href="#107">107</a></td></tr
><tr id="gr_svn22_108"

><td id="108"><a href="#108">108</a></td></tr
><tr id="gr_svn22_109"

><td id="109"><a href="#109">109</a></td></tr
><tr id="gr_svn22_110"

><td id="110"><a href="#110">110</a></td></tr
><tr id="gr_svn22_111"

><td id="111"><a href="#111">111</a></td></tr
><tr id="gr_svn22_112"

><td id="112"><a href="#112">112</a></td></tr
><tr id="gr_svn22_113"

><td id="113"><a href="#113">113</a></td></tr
><tr id="gr_svn22_114"

><td id="114"><a href="#114">114</a></td></tr
><tr id="gr_svn22_115"

><td id="115"><a href="#115">115</a></td></tr
><tr id="gr_svn22_116"

><td id="116"><a href="#116">116</a></td></tr
><tr id="gr_svn22_117"

><td id="117"><a href="#117">117</a></td></tr
><tr id="gr_svn22_118"

><td id="118"><a href="#118">118</a></td></tr
><tr id="gr_svn22_119"

><td id="119"><a href="#119">119</a></td></tr
><tr id="gr_svn22_120"

><td id="120"><a href="#120">120</a></td></tr
><tr id="gr_svn22_121"

><td id="121"><a href="#121">121</a></td></tr
><tr id="gr_svn22_122"

><td id="122"><a href="#122">122</a></td></tr
><tr id="gr_svn22_123"

><td id="123"><a href="#123">123</a></td></tr
><tr id="gr_svn22_124"

><td id="124"><a href="#124">124</a></td></tr
><tr id="gr_svn22_125"

><td id="125"><a href="#125">125</a></td></tr
><tr id="gr_svn22_126"

><td id="126"><a href="#126">126</a></td></tr
><tr id="gr_svn22_127"

><td id="127"><a href="#127">127</a></td></tr
><tr id="gr_svn22_128"

><td id="128"><a href="#128">128</a></td></tr
><tr id="gr_svn22_129"

><td id="129"><a href="#129">129</a></td></tr
><tr id="gr_svn22_130"

><td id="130"><a href="#130">130</a></td></tr
><tr id="gr_svn22_131"

><td id="131"><a href="#131">131</a></td></tr
><tr id="gr_svn22_132"

><td id="132"><a href="#132">132</a></td></tr
><tr id="gr_svn22_133"

><td id="133"><a href="#133">133</a></td></tr
><tr id="gr_svn22_134"

><td id="134"><a href="#134">134</a></td></tr
><tr id="gr_svn22_135"

><td id="135"><a href="#135">135</a></td></tr
><tr id="gr_svn22_136"

><td id="136"><a href="#136">136</a></td></tr
><tr id="gr_svn22_137"

><td id="137"><a href="#137">137</a></td></tr
><tr id="gr_svn22_138"

><td id="138"><a href="#138">138</a></td></tr
><tr id="gr_svn22_139"

><td id="139"><a href="#139">139</a></td></tr
><tr id="gr_svn22_140"

><td id="140"><a href="#140">140</a></td></tr
><tr id="gr_svn22_141"

><td id="141"><a href="#141">141</a></td></tr
><tr id="gr_svn22_142"

><td id="142"><a href="#142">142</a></td></tr
><tr id="gr_svn22_143"

><td id="143"><a href="#143">143</a></td></tr
><tr id="gr_svn22_144"

><td id="144"><a href="#144">144</a></td></tr
><tr id="gr_svn22_145"

><td id="145"><a href="#145">145</a></td></tr
><tr id="gr_svn22_146"

><td id="146"><a href="#146">146</a></td></tr
><tr id="gr_svn22_147"

><td id="147"><a href="#147">147</a></td></tr
><tr id="gr_svn22_148"

><td id="148"><a href="#148">148</a></td></tr
><tr id="gr_svn22_149"

><td id="149"><a href="#149">149</a></td></tr
><tr id="gr_svn22_150"

><td id="150"><a href="#150">150</a></td></tr
><tr id="gr_svn22_151"

><td id="151"><a href="#151">151</a></td></tr
><tr id="gr_svn22_152"

><td id="152"><a href="#152">152</a></td></tr
><tr id="gr_svn22_153"

><td id="153"><a href="#153">153</a></td></tr
><tr id="gr_svn22_154"

><td id="154"><a href="#154">154</a></td></tr
><tr id="gr_svn22_155"

><td id="155"><a href="#155">155</a></td></tr
><tr id="gr_svn22_156"

><td id="156"><a href="#156">156</a></td></tr
><tr id="gr_svn22_157"

><td id="157"><a href="#157">157</a></td></tr
><tr id="gr_svn22_158"

><td id="158"><a href="#158">158</a></td></tr
><tr id="gr_svn22_159"

><td id="159"><a href="#159">159</a></td></tr
><tr id="gr_svn22_160"

><td id="160"><a href="#160">160</a></td></tr
><tr id="gr_svn22_161"

><td id="161"><a href="#161">161</a></td></tr
><tr id="gr_svn22_162"

><td id="162"><a href="#162">162</a></td></tr
><tr id="gr_svn22_163"

><td id="163"><a href="#163">163</a></td></tr
><tr id="gr_svn22_164"

><td id="164"><a href="#164">164</a></td></tr
><tr id="gr_svn22_165"

><td id="165"><a href="#165">165</a></td></tr
><tr id="gr_svn22_166"

><td id="166"><a href="#166">166</a></td></tr
><tr id="gr_svn22_167"

><td id="167"><a href="#167">167</a></td></tr
><tr id="gr_svn22_168"

><td id="168"><a href="#168">168</a></td></tr
><tr id="gr_svn22_169"

><td id="169"><a href="#169">169</a></td></tr
><tr id="gr_svn22_170"

><td id="170"><a href="#170">170</a></td></tr
><tr id="gr_svn22_171"

><td id="171"><a href="#171">171</a></td></tr
><tr id="gr_svn22_172"

><td id="172"><a href="#172">172</a></td></tr
><tr id="gr_svn22_173"

><td id="173"><a href="#173">173</a></td></tr
><tr id="gr_svn22_174"

><td id="174"><a href="#174">174</a></td></tr
><tr id="gr_svn22_175"

><td id="175"><a href="#175">175</a></td></tr
><tr id="gr_svn22_176"

><td id="176"><a href="#176">176</a></td></tr
><tr id="gr_svn22_177"

><td id="177"><a href="#177">177</a></td></tr
><tr id="gr_svn22_178"

><td id="178"><a href="#178">178</a></td></tr
><tr id="gr_svn22_179"

><td id="179"><a href="#179">179</a></td></tr
><tr id="gr_svn22_180"

><td id="180"><a href="#180">180</a></td></tr
><tr id="gr_svn22_181"

><td id="181"><a href="#181">181</a></td></tr
><tr id="gr_svn22_182"

><td id="182"><a href="#182">182</a></td></tr
><tr id="gr_svn22_183"

><td id="183"><a href="#183">183</a></td></tr
><tr id="gr_svn22_184"

><td id="184"><a href="#184">184</a></td></tr
><tr id="gr_svn22_185"

><td id="185"><a href="#185">185</a></td></tr
><tr id="gr_svn22_186"

><td id="186"><a href="#186">186</a></td></tr
><tr id="gr_svn22_187"

><td id="187"><a href="#187">187</a></td></tr
><tr id="gr_svn22_188"

><td id="188"><a href="#188">188</a></td></tr
><tr id="gr_svn22_189"

><td id="189"><a href="#189">189</a></td></tr
><tr id="gr_svn22_190"

><td id="190"><a href="#190">190</a></td></tr
><tr id="gr_svn22_191"

><td id="191"><a href="#191">191</a></td></tr
></table></pre>

<pre><table width="100%"><tr class="nocursor"><td></td></tr></table></pre>
</td>
<td id="lines">
<pre class="prettyprint"><table width="100%"><tr class="cursor_stop cursor_hidden"><td></td></tr></table></pre>

<pre class="prettyprint lang-java"><table id="src_table_0"><tr
id=sl_svn22_1

><td class="source">/*<br></td></tr
><tr
id=sl_svn22_2

><td class="source"> *  Copyright (C) 2010 Zigabyte Corporation. All rights reserved.<br></td></tr
><tr
id=sl_svn22_3

><td class="source"> *<br></td></tr
><tr
id=sl_svn22_4

><td class="source"> *  This file is part of QuantDesk - http://code.google.com/p/quantdesk/<br></td></tr
><tr
id=sl_svn22_5

><td class="source"> *<br></td></tr
><tr
id=sl_svn22_6

><td class="source"> *  QuantDesk is free software: you can redistribute it and/or modify<br></td></tr
><tr
id=sl_svn22_7

><td class="source"> *  it under the terms of the GNU General Public License as published by<br></td></tr
><tr
id=sl_svn22_8

><td class="source"> *  the Free Software Foundation, either version 3 of the License, or<br></td></tr
><tr
id=sl_svn22_9

><td class="source"> *  (at your option) any later version.<br></td></tr
><tr
id=sl_svn22_10

><td class="source"> *<br></td></tr
><tr
id=sl_svn22_11

><td class="source"> *  QuantDesk is distributed in the hope that it will be useful,<br></td></tr
><tr
id=sl_svn22_12

><td class="source"> *  but WITHOUT ANY WARRANTY; without even the implied warranty of<br></td></tr
><tr
id=sl_svn22_13

><td class="source"> *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the<br></td></tr
><tr
id=sl_svn22_14

><td class="source"> *  GNU General Public License for more details.<br></td></tr
><tr
id=sl_svn22_15

><td class="source"> *<br></td></tr
><tr
id=sl_svn22_16

><td class="source"> *  You should have received a copy of the GNU General Public License<br></td></tr
><tr
id=sl_svn22_17

><td class="source"> *  along with QuantDesk.  If not, see &lt;http://www.gnu.org/licenses/&gt;.<br></td></tr
><tr
id=sl_svn22_18

><td class="source"> */<br></td></tr
><tr
id=sl_svn22_19

><td class="source"><br></td></tr
><tr
id=sl_svn22_20

><td class="source">package com.winance.optimizer.symbols;<br></td></tr
><tr
id=sl_svn22_21

><td class="source"><br></td></tr
><tr
id=sl_svn22_22

><td class="source">import java.util.ArrayList;<br></td></tr
><tr
id=sl_svn22_23

><td class="source">import java.util.Calendar;<br></td></tr
><tr
id=sl_svn22_24

><td class="source">import java.util.Collections;<br></td></tr
><tr
id=sl_svn22_25

><td class="source">import java.util.Date;<br></td></tr
><tr
id=sl_svn22_26

><td class="source">import java.util.HashMap;<br></td></tr
><tr
id=sl_svn22_27

><td class="source">import java.util.TreeMap;<br></td></tr
><tr
id=sl_svn22_28

><td class="source"><br></td></tr
><tr
id=sl_svn22_29

><td class="source"><br></td></tr
><tr
id=sl_svn22_30

><td class="source">public class SymbolDataCollection<br></td></tr
><tr
id=sl_svn22_31

><td class="source">{<br></td></tr
><tr
id=sl_svn22_32

><td class="source">    public class SymbolDataItem implements Comparable&lt;SymbolDataItem&gt;<br></td></tr
><tr
id=sl_svn22_33

><td class="source">    {<br></td></tr
><tr
id=sl_svn22_34

><td class="source">        public SymbolDataItem(Calendar date, double open, double high, double low, double close, double volume)<br></td></tr
><tr
id=sl_svn22_35

><td class="source">        {<br></td></tr
><tr
id=sl_svn22_36

><td class="source">            this.date = date;<br></td></tr
><tr
id=sl_svn22_37

><td class="source">            this.open = open;<br></td></tr
><tr
id=sl_svn22_38

><td class="source">            this.high = high;<br></td></tr
><tr
id=sl_svn22_39

><td class="source">            this.low = low;<br></td></tr
><tr
id=sl_svn22_40

><td class="source">            this.close = close;<br></td></tr
><tr
id=sl_svn22_41

><td class="source">            this.volume = volume;<br></td></tr
><tr
id=sl_svn22_42

><td class="source">        }<br></td></tr
><tr
id=sl_svn22_43

><td class="source">        public Calendar date;<br></td></tr
><tr
id=sl_svn22_44

><td class="source">        public double open;<br></td></tr
><tr
id=sl_svn22_45

><td class="source">        public double high;<br></td></tr
><tr
id=sl_svn22_46

><td class="source">        public double low;<br></td></tr
><tr
id=sl_svn22_47

><td class="source">        public double close;<br></td></tr
><tr
id=sl_svn22_48

><td class="source">        public double volume;<br></td></tr
><tr
id=sl_svn22_49

><td class="source"><br></td></tr
><tr
id=sl_svn22_50

><td class="source">        public int compareTo(SymbolDataItem o)<br></td></tr
><tr
id=sl_svn22_51

><td class="source">        {<br></td></tr
><tr
id=sl_svn22_52

><td class="source">            long val = this.date.getTimeInMillis() - o.date.getTimeInMillis();<br></td></tr
><tr
id=sl_svn22_53

><td class="source">            if(val&lt;0)<br></td></tr
><tr
id=sl_svn22_54

><td class="source">                return -1;<br></td></tr
><tr
id=sl_svn22_55

><td class="source">            if(val&gt;0)<br></td></tr
><tr
id=sl_svn22_56

><td class="source">                return 1;<br></td></tr
><tr
id=sl_svn22_57

><td class="source">            return 0;<br></td></tr
><tr
id=sl_svn22_58

><td class="source">        }<br></td></tr
><tr
id=sl_svn22_59

><td class="source">    }<br></td></tr
><tr
id=sl_svn22_60

><td class="source"><br></td></tr
><tr
id=sl_svn22_61

><td class="source">    public SymbolDataCollection()<br></td></tr
><tr
id=sl_svn22_62

><td class="source">    {<br></td></tr
><tr
id=sl_svn22_63

><td class="source">        data = new TreeMap&lt;Long, SymbolDataItem&gt;();<br></td></tr
><tr
id=sl_svn22_64

><td class="source">        yearlyUnadjustedRatios = new HashMap&lt;Integer, ArrayList&lt;SymbolRatioItem&gt;&gt;();<br></td></tr
><tr
id=sl_svn22_65

><td class="source">        yearlyAdjustedRatios = new HashMap&lt;Integer, ArrayList&lt;SymbolRatioItem&gt;&gt;();<br></td></tr
><tr
id=sl_svn22_66

><td class="source">    }<br></td></tr
><tr
id=sl_svn22_67

><td class="source"><br></td></tr
><tr
id=sl_svn22_68

><td class="source">    public void addDataItem(Date date, double open, double high, double low, double close, double volume)<br></td></tr
><tr
id=sl_svn22_69

><td class="source">    {<br></td></tr
><tr
id=sl_svn22_70

><td class="source">        Calendar c = Calendar.getInstance();<br></td></tr
><tr
id=sl_svn22_71

><td class="source">        c.setTime(date);<br></td></tr
><tr
id=sl_svn22_72

><td class="source">        SymbolDataItem item = new SymbolDataItem(c, open, high, low, close, volume);<br></td></tr
><tr
id=sl_svn22_73

><td class="source">        data.put(c.getTimeInMillis(), item);<br></td></tr
><tr
id=sl_svn22_74

><td class="source">    }<br></td></tr
><tr
id=sl_svn22_75

><td class="source"><br></td></tr
><tr
id=sl_svn22_76

><td class="source">    public ArrayList&lt;SymbolDataItem&gt; getFromInterval(Calendar startDate, Calendar endDate)<br></td></tr
><tr
id=sl_svn22_77

><td class="source">    {<br></td></tr
><tr
id=sl_svn22_78

><td class="source">        ArrayList&lt;SymbolDataItem&gt; ret = new ArrayList&lt;SymbolDataItem&gt;();<br></td></tr
><tr
id=sl_svn22_79

><td class="source">        ret.addAll(data.subMap(startDate.getTimeInMillis(), true, endDate.getTimeInMillis(), false).values());<br></td></tr
><tr
id=sl_svn22_80

><td class="source"><br></td></tr
><tr
id=sl_svn22_81

><td class="source">        return ret;<br></td></tr
><tr
id=sl_svn22_82

><td class="source">    }<br></td></tr
><tr
id=sl_svn22_83

><td class="source"><br></td></tr
><tr
id=sl_svn22_84

><td class="source">    public ArrayList&lt;SymbolRatioItem&gt; getUnadjustedRatiosForYear(int year)<br></td></tr
><tr
id=sl_svn22_85

><td class="source">    {<br></td></tr
><tr
id=sl_svn22_86

><td class="source">        ArrayList&lt;SymbolRatioItem&gt; ret = new ArrayList&lt;SymbolRatioItem&gt;();<br></td></tr
><tr
id=sl_svn22_87

><td class="source">        if(yearlyUnadjustedRatios.containsKey(year))<br></td></tr
><tr
id=sl_svn22_88

><td class="source">        {<br></td></tr
><tr
id=sl_svn22_89

><td class="source">            Collections.copy(ret, yearlyUnadjustedRatios.get(year));<br></td></tr
><tr
id=sl_svn22_90

><td class="source">            return ret;<br></td></tr
><tr
id=sl_svn22_91

><td class="source">        }<br></td></tr
><tr
id=sl_svn22_92

><td class="source"><br></td></tr
><tr
id=sl_svn22_93

><td class="source">        ret = calculateUnadjustedRatiosForYear(year);<br></td></tr
><tr
id=sl_svn22_94

><td class="source">        ArrayList&lt;SymbolRatioItem&gt; cacheItem = new ArrayList&lt;SymbolRatioItem&gt;();<br></td></tr
><tr
id=sl_svn22_95

><td class="source">        cacheItem.addAll(ret);<br></td></tr
><tr
id=sl_svn22_96

><td class="source">        yearlyUnadjustedRatios.put(year, cacheItem);<br></td></tr
><tr
id=sl_svn22_97

><td class="source"><br></td></tr
><tr
id=sl_svn22_98

><td class="source">        return ret;<br></td></tr
><tr
id=sl_svn22_99

><td class="source">    }<br></td></tr
><tr
id=sl_svn22_100

><td class="source"><br></td></tr
><tr
id=sl_svn22_101

><td class="source">    public ArrayList&lt;SymbolRatioItem&gt; getAdjustedRatiosForYear(int year)<br></td></tr
><tr
id=sl_svn22_102

><td class="source">    {<br></td></tr
><tr
id=sl_svn22_103

><td class="source">        ArrayList&lt;SymbolRatioItem&gt; ret = new ArrayList&lt;SymbolRatioItem&gt;();<br></td></tr
><tr
id=sl_svn22_104

><td class="source">        if(yearlyAdjustedRatios.containsKey(year))<br></td></tr
><tr
id=sl_svn22_105

><td class="source">        {<br></td></tr
><tr
id=sl_svn22_106

><td class="source">            ret.addAll(yearlyAdjustedRatios.get(year));<br></td></tr
><tr
id=sl_svn22_107

><td class="source">            return ret;<br></td></tr
><tr
id=sl_svn22_108

><td class="source">        }<br></td></tr
><tr
id=sl_svn22_109

><td class="source"><br></td></tr
><tr
id=sl_svn22_110

><td class="source">        ret = calculateAdjustedRatiosForYear(year);<br></td></tr
><tr
id=sl_svn22_111

><td class="source">        ArrayList&lt;SymbolRatioItem&gt; cacheItem = new ArrayList&lt;SymbolRatioItem&gt;();<br></td></tr
><tr
id=sl_svn22_112

><td class="source">        cacheItem.addAll(ret);<br></td></tr
><tr
id=sl_svn22_113

><td class="source">        yearlyAdjustedRatios.put(year, cacheItem);<br></td></tr
><tr
id=sl_svn22_114

><td class="source"><br></td></tr
><tr
id=sl_svn22_115

><td class="source">        return ret;<br></td></tr
><tr
id=sl_svn22_116

><td class="source"><br></td></tr
><tr
id=sl_svn22_117

><td class="source">    }<br></td></tr
><tr
id=sl_svn22_118

><td class="source"><br></td></tr
><tr
id=sl_svn22_119

><td class="source"><br></td></tr
><tr
id=sl_svn22_120

><td class="source">    public int size()<br></td></tr
><tr
id=sl_svn22_121

><td class="source">    {<br></td></tr
><tr
id=sl_svn22_122

><td class="source">        return data.size();<br></td></tr
><tr
id=sl_svn22_123

><td class="source">    }<br></td></tr
><tr
id=sl_svn22_124

><td class="source"><br></td></tr
><tr
id=sl_svn22_125

><td class="source">    private ArrayList&lt;SymbolRatioItem&gt; calculateUnadjustedRatiosForYear(int year)<br></td></tr
><tr
id=sl_svn22_126

><td class="source">    {<br></td></tr
><tr
id=sl_svn22_127

><td class="source">        ArrayList&lt;SymbolRatioItem&gt; ret = new ArrayList&lt;SymbolRatioItem&gt;();<br></td></tr
><tr
id=sl_svn22_128

><td class="source">        <br></td></tr
><tr
id=sl_svn22_129

><td class="source">        Calendar startDate = Calendar.getInstance();<br></td></tr
><tr
id=sl_svn22_130

><td class="source">        startDate.clear();<br></td></tr
><tr
id=sl_svn22_131

><td class="source">        startDate.set(year, 0, 1);<br></td></tr
><tr
id=sl_svn22_132

><td class="source"><br></td></tr
><tr
id=sl_svn22_133

><td class="source">        Calendar endDate = Calendar.getInstance();<br></td></tr
><tr
id=sl_svn22_134

><td class="source">        endDate.clear();<br></td></tr
><tr
id=sl_svn22_135

><td class="source">        endDate.set(year+1, 0, 1);<br></td></tr
><tr
id=sl_svn22_136

><td class="source"><br></td></tr
><tr
id=sl_svn22_137

><td class="source">        ArrayList&lt;SymbolDataItem&gt; prices = getFromInterval(startDate, endDate);<br></td></tr
><tr
id=sl_svn22_138

><td class="source"><br></td></tr
><tr
id=sl_svn22_139

><td class="source">        for(int i=1;i&lt;prices.size();i++)<br></td></tr
><tr
id=sl_svn22_140

><td class="source">        {<br></td></tr
><tr
id=sl_svn22_141

><td class="source">            SymbolDataCollection.SymbolDataItem currentItem = prices.get(i);<br></td></tr
><tr
id=sl_svn22_142

><td class="source">            SymbolDataCollection.SymbolDataItem previousItem = prices.get(i-1);<br></td></tr
><tr
id=sl_svn22_143

><td class="source"><br></td></tr
><tr
id=sl_svn22_144

><td class="source">            double ratio = currentItem.open / previousItem.open;<br></td></tr
><tr
id=sl_svn22_145

><td class="source"><br></td></tr
><tr
id=sl_svn22_146

><td class="source">            ret.add(new SymbolRatioItem(currentItem.date, ratio));<br></td></tr
><tr
id=sl_svn22_147

><td class="source">        }<br></td></tr
><tr
id=sl_svn22_148

><td class="source"><br></td></tr
><tr
id=sl_svn22_149

><td class="source">        return ret;<br></td></tr
><tr
id=sl_svn22_150

><td class="source">    }<br></td></tr
><tr
id=sl_svn22_151

><td class="source"><br></td></tr
><tr
id=sl_svn22_152

><td class="source">    private ArrayList&lt;SymbolRatioItem&gt; calculateAdjustedRatiosForYear(int year)<br></td></tr
><tr
id=sl_svn22_153

><td class="source">    {<br></td></tr
><tr
id=sl_svn22_154

><td class="source">        ArrayList&lt;SymbolRatioItem&gt; ret = new ArrayList&lt;SymbolRatioItem&gt;();<br></td></tr
><tr
id=sl_svn22_155

><td class="source"><br></td></tr
><tr
id=sl_svn22_156

><td class="source">        Calendar startDate = Calendar.getInstance();<br></td></tr
><tr
id=sl_svn22_157

><td class="source">        startDate.clear();<br></td></tr
><tr
id=sl_svn22_158

><td class="source">        startDate.set(year, 0, 1);<br></td></tr
><tr
id=sl_svn22_159

><td class="source"><br></td></tr
><tr
id=sl_svn22_160

><td class="source">        Calendar endDate = Calendar.getInstance();<br></td></tr
><tr
id=sl_svn22_161

><td class="source">        endDate.clear();<br></td></tr
><tr
id=sl_svn22_162

><td class="source">        endDate.set(year+1, 0, 1);<br></td></tr
><tr
id=sl_svn22_163

><td class="source"><br></td></tr
><tr
id=sl_svn22_164

><td class="source">        ArrayList&lt;SymbolDataItem&gt; prices = getFromInterval(startDate, endDate);<br></td></tr
><tr
id=sl_svn22_165

><td class="source"><br></td></tr
><tr
id=sl_svn22_166

><td class="source">        long datesDifference = 0;<br></td></tr
><tr
id=sl_svn22_167

><td class="source">        double daysDifference =0;<br></td></tr
><tr
id=sl_svn22_168

><td class="source">        double returnRatio = 0;<br></td></tr
><tr
id=sl_svn22_169

><td class="source">        for(int i=1;i&lt;prices.size();i++)<br></td></tr
><tr
id=sl_svn22_170

><td class="source">        {<br></td></tr
><tr
id=sl_svn22_171

><td class="source">            SymbolDataCollection.SymbolDataItem currentItem = prices.get(i);<br></td></tr
><tr
id=sl_svn22_172

><td class="source">            SymbolDataCollection.SymbolDataItem previousItem = prices.get(i-1);<br></td></tr
><tr
id=sl_svn22_173

><td class="source"><br></td></tr
><tr
id=sl_svn22_174

><td class="source">            datesDifference = currentItem.date.getTimeInMillis() - previousItem.date.getTimeInMillis();<br></td></tr
><tr
id=sl_svn22_175

><td class="source"><br></td></tr
><tr
id=sl_svn22_176

><td class="source">            daysDifference = datesDifference / 1000. / 60. / 60. / 24.;<br></td></tr
><tr
id=sl_svn22_177

><td class="source"><br></td></tr
><tr
id=sl_svn22_178

><td class="source">            returnRatio = currentItem.open / previousItem.open;<br></td></tr
><tr
id=sl_svn22_179

><td class="source"><br></td></tr
><tr
id=sl_svn22_180

><td class="source">            returnRatio = Math.pow(returnRatio, 1. / daysDifference);<br></td></tr
><tr
id=sl_svn22_181

><td class="source"><br></td></tr
><tr
id=sl_svn22_182

><td class="source">            ret.add(new SymbolRatioItem(currentItem.date, returnRatio));<br></td></tr
><tr
id=sl_svn22_183

><td class="source">        }<br></td></tr
><tr
id=sl_svn22_184

><td class="source"><br></td></tr
><tr
id=sl_svn22_185

><td class="source">        return ret;<br></td></tr
><tr
id=sl_svn22_186

><td class="source">    }<br></td></tr
><tr
id=sl_svn22_187

><td class="source"><br></td></tr
><tr
id=sl_svn22_188

><td class="source">    private TreeMap&lt;Long, SymbolDataItem&gt; data;<br></td></tr
><tr
id=sl_svn22_189

><td class="source">    private HashMap&lt;Integer, ArrayList&lt;SymbolRatioItem&gt;&gt; yearlyUnadjustedRatios;<br></td></tr
><tr
id=sl_svn22_190

><td class="source">    private HashMap&lt;Integer, ArrayList&lt;SymbolRatioItem&gt;&gt; yearlyAdjustedRatios;<br></td></tr
><tr
id=sl_svn22_191

><td class="source">}<br></td></tr
></table></pre>

<pre class="prettyprint"><table width="100%"><tr class="cursor_stop cursor_hidden"><td></td></tr></table></pre>
</td>
</tr></table>
<script type="text/javascript">
 var lineNumUnderMouse = -1;
 
 function gutterOver(num) {
 gutterOut();
 var newTR = document.getElementById('gr_svn22_' + num);
 if (newTR) {
 newTR.className = 'undermouse';
 }
 lineNumUnderMouse = num;
 }
 function gutterOut() {
 if (lineNumUnderMouse != -1) {
 var oldTR = document.getElementById(
 'gr_svn22_' + lineNumUnderMouse);
 if (oldTR) {
 oldTR.className = '';
 }
 lineNumUnderMouse = -1;
 }
 }
 var numsGenState = {table_base_id: 'nums_table_'};
 var srcGenState = {table_base_id: 'src_table_'};
 var alignerRunning = false;
 var startOver = false;
 function setLineNumberHeights() {
 if (alignerRunning) {
 startOver = true;
 return;
 }
 numsGenState.chunk_id = 0;
 numsGenState.table = document.getElementById('nums_table_0');
 numsGenState.row_num = 0;
 srcGenState.chunk_id = 0;
 srcGenState.table = document.getElementById('src_table_0');
 srcGenState.row_num = 0;
 alignerRunning = true;
 continueToSetLineNumberHeights();
 }
 function rowGenerator(genState) {
 if (genState.row_num < genState.table.rows.length) {
 var currentRow = genState.table.rows[genState.row_num];
 genState.row_num++;
 return currentRow;
 }
 var newTable = document.getElementById(
 genState.table_base_id + (genState.chunk_id + 1));
 if (newTable) {
 genState.chunk_id++;
 genState.row_num = 0;
 genState.table = newTable;
 return genState.table.rows[0];
 }
 return null;
 }
 var MAX_ROWS_PER_PASS = 1000;
 function continueToSetLineNumberHeights() {
 var rowsInThisPass = 0;
 var numRow = 1;
 var srcRow = 1;
 while (numRow && srcRow && rowsInThisPass < MAX_ROWS_PER_PASS) {
 numRow = rowGenerator(numsGenState);
 srcRow = rowGenerator(srcGenState);
 rowsInThisPass++;
 if (numRow && srcRow) {
 if (numRow.offsetHeight != srcRow.offsetHeight) {
 numRow.firstChild.style.height = srcRow.offsetHeight + 'px';
 }
 }
 }
 if (rowsInThisPass >= MAX_ROWS_PER_PASS) {
 setTimeout(continueToSetLineNumberHeights, 10);
 } else {
 alignerRunning = false;
 if (startOver) {
 startOver = false;
 setTimeout(setLineNumberHeights, 500);
 }
 }
 }
 // Do 2 complete passes, because there can be races
 // between this code and prettify.
 startOver = true;
 setTimeout(setLineNumberHeights, 250);
 window.onresize = setLineNumberHeights;
</script>

 
 
 <div id="log">
 <div style="text-align:right">
 <a class="ifCollapse" href="#" onclick="_toggleMeta('', 'p', 'quantdesk', this)">Show details</a>
 <a class="ifExpand" href="#" onclick="_toggleMeta('', 'p', 'quantdesk', this)">Hide details</a>
 </div>
 <div class="ifExpand">
 
 <div class="pmeta_bubble_bg" style="border:1px solid white">
 <div class="round4"></div>
 <div class="round2"></div>
 <div class="round1"></div>
 <div class="box-inner">
 <div id="changelog">
 <p>Change log</p>
 <div>
 <a href="/p/quantdesk/source/detail?spec=svn22&r=22">r22</a>
 by atramos
 on May 23, 2010
 &nbsp; <a href="/p/quantdesk/source/diff?spec=svn22&r=22&amp;format=side&amp;path=/QuantDesk_YahooData/java/com/winance/optimizer/symbols/SymbolDataCollection.java&amp;old_path=/QuantDesk_YahooData/java/com/winance/optimizer/symbols/SymbolDataCollection.java&amp;old=">Diff</a>
 </div>
 <pre>Initial import.</pre>
 </div>
 
 
 
 
 
 
 <script type="text/javascript">
 var detail_url = '/p/quantdesk/source/detail?r=22&spec=svn22';
 var publish_url = '/p/quantdesk/source/detail?r=22&spec=svn22#publish';
 // describe the paths of this revision in javascript.
 var changed_paths = [];
 var changed_urls = [];
 
 changed_paths.push('/QuantDesk_YahooData/.classpath');
 changed_urls.push('/p/quantdesk/source/browse/QuantDesk_YahooData/.classpath?r=22&spec=svn22');
 
 
 changed_paths.push('/QuantDesk_YahooData/.project');
 changed_urls.push('/p/quantdesk/source/browse/QuantDesk_YahooData/.project?r=22&spec=svn22');
 
 
 changed_paths.push('/QuantDesk_YahooData/java');
 changed_urls.push('/p/quantdesk/source/browse/QuantDesk_YahooData/java?r=22&spec=svn22');
 
 
 changed_paths.push('/QuantDesk_YahooData/java/com');
 changed_urls.push('/p/quantdesk/source/browse/QuantDesk_YahooData/java/com?r=22&spec=svn22');
 
 
 changed_paths.push('/QuantDesk_YahooData/java/com/winance');
 changed_urls.push('/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance?r=22&spec=svn22');
 
 
 changed_paths.push('/QuantDesk_YahooData/java/com/winance/optimizer');
 changed_urls.push('/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer?r=22&spec=svn22');
 
 
 changed_paths.push('/QuantDesk_YahooData/java/com/winance/optimizer/symbols');
 changed_urls.push('/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols?r=22&spec=svn22');
 
 
 changed_paths.push('/QuantDesk_YahooData/java/com/winance/optimizer/symbols/Symbol.java');
 changed_urls.push('/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/Symbol.java?r=22&spec=svn22');
 
 
 changed_paths.push('/QuantDesk_YahooData/java/com/winance/optimizer/symbols/SymbolDataCollection.java');
 changed_urls.push('/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/SymbolDataCollection.java?r=22&spec=svn22');
 
 var selected_path = '/QuantDesk_YahooData/java/com/winance/optimizer/symbols/SymbolDataCollection.java';
 
 
 changed_paths.push('/QuantDesk_YahooData/java/com/winance/optimizer/symbols/SymbolRatioItem.java');
 changed_urls.push('/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/SymbolRatioItem.java?r=22&spec=svn22');
 
 
 changed_paths.push('/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders');
 changed_urls.push('/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders?r=22&spec=svn22');
 
 
 changed_paths.push('/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders/ISymbolsLoader.java');
 changed_urls.push('/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders/ISymbolsLoader.java?r=22&spec=svn22');
 
 
 changed_paths.push('/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders/yahoo');
 changed_urls.push('/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders/yahoo?r=22&spec=svn22');
 
 
 changed_paths.push('/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders/yahoo/YahooCsvLoader.java');
 changed_urls.push('/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders/yahoo/YahooCsvLoader.java?r=22&spec=svn22');
 
 
 changed_paths.push('/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders/yahoo/YahooLoader.java');
 changed_urls.push('/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders/yahoo/YahooLoader.java?r=22&spec=svn22');
 
 
 changed_paths.push('/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders/yahoo/YahooNetworkLoader.java');
 changed_urls.push('/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders/yahoo/YahooNetworkLoader.java?r=22&spec=svn22');
 
 
 function getCurrentPageIndex() {
 for (var i = 0; i < changed_paths.length; i++) {
 if (selected_path == changed_paths[i]) {
 return i;
 }
 }
 }
 function getNextPage() {
 var i = getCurrentPageIndex();
 if (i < changed_paths.length - 1) {
 return changed_urls[i + 1];
 }
 return null;
 }
 function getPreviousPage() {
 var i = getCurrentPageIndex();
 if (i > 0) {
 return changed_urls[i - 1];
 }
 return null;
 }
 function gotoNextPage() {
 var page = getNextPage();
 if (!page) {
 page = detail_url;
 }
 window.location = page;
 }
 function gotoPreviousPage() {
 var page = getPreviousPage();
 if (!page) {
 page = detail_url;
 }
 window.location = page;
 }
 function gotoDetailPage() {
 window.location = detail_url;
 }
 function gotoPublishPage() {
 window.location = publish_url;
 }
</script>
 
 <style type="text/css">
 #review_nav {
 border-top: 3px solid white;
 padding-top: 6px;
 margin-top: 1em;
 }
 #review_nav td {
 vertical-align: middle;
 }
 #review_nav select {
 margin: .5em 0;
 }
 </style>
 <div id="review_nav">
 <table><tr><td>Go to:&nbsp;</td><td>
 <select name="files_in_rev" onchange="window.location=this.value">
 
 <option value="/p/quantdesk/source/browse/QuantDesk_YahooData/.classpath?r=22&amp;spec=svn22"
 
 >/QuantDesk_YahooData/.classpath</option>
 
 <option value="/p/quantdesk/source/browse/QuantDesk_YahooData/.project?r=22&amp;spec=svn22"
 
 >/QuantDesk_YahooData/.project</option>
 
 <option value="/p/quantdesk/source/browse/QuantDesk_YahooData/java?r=22&amp;spec=svn22"
 
 >/QuantDesk_YahooData/java</option>
 
 <option value="/p/quantdesk/source/browse/QuantDesk_YahooData/java/com?r=22&amp;spec=svn22"
 
 >/QuantDesk_YahooData/java/com</option>
 
 <option value="/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance?r=22&amp;spec=svn22"
 
 >...tDesk_YahooData/java/com/winance</option>
 
 <option value="/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer?r=22&amp;spec=svn22"
 
 >...oData/java/com/winance/optimizer</option>
 
 <option value="/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols?r=22&amp;spec=svn22"
 
 >...va/com/winance/optimizer/symbols</option>
 
 <option value="/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/Symbol.java?r=22&amp;spec=svn22"
 
 >...ce/optimizer/symbols/Symbol.java</option>
 
 <option value="/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/SymbolDataCollection.java?r=22&amp;spec=svn22"
 selected="selected"
 >...ymbols/SymbolDataCollection.java</option>
 
 <option value="/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/SymbolRatioItem.java?r=22&amp;spec=svn22"
 
 >...zer/symbols/SymbolRatioItem.java</option>
 
 <option value="/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders?r=22&amp;spec=svn22"
 
 >...inance/optimizer/symbols/loaders</option>
 
 <option value="/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders/ISymbolsLoader.java?r=22&amp;spec=svn22"
 
 >...bols/loaders/ISymbolsLoader.java</option>
 
 <option value="/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders/yahoo?r=22&amp;spec=svn22"
 
 >.../optimizer/symbols/loaders/yahoo</option>
 
 <option value="/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders/yahoo/YahooCsvLoader.java?r=22&amp;spec=svn22"
 
 >...oaders/yahoo/YahooCsvLoader.java</option>
 
 <option value="/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders/yahoo/YahooLoader.java?r=22&amp;spec=svn22"
 
 >...s/loaders/yahoo/YahooLoader.java</option>
 
 <option value="/p/quantdesk/source/browse/QuantDesk_YahooData/java/com/winance/optimizer/symbols/loaders/yahoo/YahooNetworkLoader.java?r=22&amp;spec=svn22"
 
 >...rs/yahoo/YahooNetworkLoader.java</option>
 
 </select>
 </td></tr></table>
 
 
 



 
 </div>
 
 
 </div>
 <div class="round1"></div>
 <div class="round2"></div>
 <div class="round4"></div>
 </div>
 <div class="pmeta_bubble_bg" style="border:1px solid white">
 <div class="round4"></div>
 <div class="round2"></div>
 <div class="round1"></div>
 <div class="box-inner">
 <div id="older_bubble">
 <p>Older revisions</p>
 
 <a href="/p/quantdesk/source/list?path=/QuantDesk_YahooData/java/com/winance/optimizer/symbols/SymbolDataCollection.java&start=22">All revisions of this file</a>
 </div>
 </div>
 <div class="round1"></div>
 <div class="round2"></div>
 <div class="round4"></div>
 </div>
 <div class="pmeta_bubble_bg" style="border:1px solid white">
 <div class="round4"></div>
 <div class="round2"></div>
 <div class="round1"></div>
 <div class="box-inner">
 <div id="fileinfo_bubble">
 <p>File info</p>
 
 <div>Size: 6230 bytes,
 191 lines</div>
 
 <div><a href="http://quantdesk.googlecode.com/svn-history/r22/QuantDesk_YahooData/java/com/winance/optimizer/symbols/SymbolDataCollection.java">View raw file</a></div>
 </div>
 
 </div>
 <div class="round1"></div>
 <div class="round2"></div>
 <div class="round4"></div>
 </div>
 </div>
 </div>


</div>
</div>

 <script src="http://www.gstatic.com/codesite/ph/13637207143931721394/js/prettify/prettify.js"></script>

<script type="text/javascript">prettyPrint();</script>

<script src="http://www.gstatic.com/codesite/ph/13637207143931721394/js/source_file_scripts.js"></script>

 <script type="text/javascript" src="http://kibbles.googlecode.com/files/kibbles-1.3.1.comp.js"></script>
 <script type="text/javascript">
 var lastStop = null;
 var initilized = false;
 
 function updateCursor(next, prev) {
 if (prev && prev.element) {
 prev.element.className = 'cursor_stop cursor_hidden';
 }
 if (next && next.element) {
 next.element.className = 'cursor_stop cursor';
 lastStop = next.index;
 }
 }
 
 function pubRevealed(data) {
 updateCursorForCell(data.cellId, 'cursor_stop cursor_hidden');
 if (initilized) {
 reloadCursors();
 }
 }
 
 function draftRevealed(data) {
 updateCursorForCell(data.cellId, 'cursor_stop cursor_hidden');
 if (initilized) {
 reloadCursors();
 }
 }
 
 function draftDestroyed(data) {
 updateCursorForCell(data.cellId, 'nocursor');
 if (initilized) {
 reloadCursors();
 }
 }
 function reloadCursors() {
 kibbles.skipper.reset();
 loadCursors();
 if (lastStop != null) {
 kibbles.skipper.setCurrentStop(lastStop);
 }
 }
 // possibly the simplest way to insert any newly added comments
 // is to update the class of the corresponding cursor row,
 // then refresh the entire list of rows.
 function updateCursorForCell(cellId, className) {
 var cell = document.getElementById(cellId);
 // we have to go two rows back to find the cursor location
 var row = getPreviousElement(cell.parentNode);
 row.className = className;
 }
 // returns the previous element, ignores text nodes.
 function getPreviousElement(e) {
 var element = e.previousSibling;
 if (element.nodeType == 3) {
 element = element.previousSibling;
 }
 if (element && element.tagName) {
 return element;
 }
 }
 function loadCursors() {
 // register our elements with skipper
 var elements = CR_getElements('*', 'cursor_stop');
 var len = elements.length;
 for (var i = 0; i < len; i++) {
 var element = elements[i]; 
 element.className = 'cursor_stop cursor_hidden';
 kibbles.skipper.append(element);
 }
 }
 function toggleComments() {
 CR_toggleCommentDisplay();
 reloadCursors();
 }
 function keysOnLoadHandler() {
 // setup skipper
 kibbles.skipper.addStopListener(
 kibbles.skipper.LISTENER_TYPE.PRE, updateCursor);
 // Set the 'offset' option to return the middle of the client area
 // an option can be a static value, or a callback
 kibbles.skipper.setOption('padding_top', 50);
 // Set the 'offset' option to return the middle of the client area
 // an option can be a static value, or a callback
 kibbles.skipper.setOption('padding_bottom', 100);
 // Register our keys
 kibbles.skipper.addFwdKey("n");
 kibbles.skipper.addRevKey("p");
 kibbles.keys.addKeyPressListener(
 'u', function() { window.location = detail_url; });
 kibbles.keys.addKeyPressListener(
 'r', function() { window.location = detail_url + '#publish'; });
 
 kibbles.keys.addKeyPressListener('j', gotoNextPage);
 kibbles.keys.addKeyPressListener('k', gotoPreviousPage);
 
 
 }
 window.onload = function() {keysOnLoadHandler();};
 </script>

<!-- code review support -->
<script src="http://www.gstatic.com/codesite/ph/13637207143931721394/js/code_review_scripts.js"></script>
<script type="text/javascript">
 
 // the comment form template
 var form = '<div class="draft"><div class="header"><span class="title">Draft comment:</span></div>' +
 '<div class="body"><form onsubmit="return false;"><textarea id="$ID">$BODY</textarea><br>$ACTIONS</form></div>' +
 '</div>';
 // the comment "plate" template used for both draft and published comment "plates".
 var draft_comment = '<div class="draft" ondblclick="$ONDBLCLICK">' +
 '<div class="header"><span class="title">Draft comment:</span><span class="actions">$ACTIONS</span></div>' +
 '<pre id="$ID" class="body">$BODY</pre>' +
 '</div>';
 var published_comment = '<div class="published">' +
 '<div class="header"><span class="title"><a href="$PROFILE_URL">$AUTHOR:</a></span><div>' +
 '<pre id="$ID" class="body">$BODY</pre>' +
 '</div>';

 function showPublishInstructions() {
 var element = document.getElementById('review_instr');
 if (element) {
 element.className = 'opened';
 }
 }
 function revsOnLoadHandler() {
 // register our source container with the commenting code
 var paths = {'svn22': '/QuantDesk_YahooData/java/com/winance/optimizer/symbols/SymbolDataCollection.java'}
 CR_setup('', 'p', 'quantdesk', '', 'svn22', paths,
 '3d9c2eb32fa665c48bb7a870307cd8bd', CR_BrowseIntegrationFactory);
 // register our hidden ui elements with the code commenting code ui builder.
 CR_registerLayoutElement('form', form);
 CR_registerLayoutElement('draft_comment', draft_comment);
 CR_registerLayoutElement('published_comment', published_comment);
 
 CR_registerActivityListener(CR_ACTIVITY_TYPE.REVEAL_DRAFT_PLATE, showPublishInstructions);
 
 CR_registerActivityListener(CR_ACTIVITY_TYPE.REVEAL_PUB_PLATE, pubRevealed);
 CR_registerActivityListener(CR_ACTIVITY_TYPE.REVEAL_DRAFT_PLATE, draftRevealed);
 CR_registerActivityListener(CR_ACTIVITY_TYPE.DISCARD_DRAFT_COMMENT, draftDestroyed);
 
 
 
 
 
 
 
 
 
 var initilized = true;
 reloadCursors();
 }
 window.onload = function() {keysOnLoadHandler(); revsOnLoadHandler();};
</script>
<script type="text/javascript" src="http://www.gstatic.com/codesite/ph/13637207143931721394/js/dit_scripts_20081013.js"></script>

 
 
 <script type="text/javascript" src="http://www.gstatic.com/codesite/ph/13637207143931721394/js/core_scripts_20081103.js"></script>
 <script type="text/javascript" src="/js/codesite_product_dictionary_ph.pack.04102009.js"></script>
 </div>
<div id="footer" dir="ltr">
 
 <div class="text">
 
 &copy;2010 Google -
 <a href="/projecthosting/terms.html">Terms</a> -
 <a href="http://www.google.com/privacy.html">Privacy</a> -
 <a href="/p/support/">Project Hosting Help</a>
 
 </div>
</div>

 <div class="hostedBy" style="margin-top: -20px;">
 <span style="vertical-align: top;">Powered by <a href="http://code.google.com/projecthosting/">Google Project Hosting</a></span>
 </div>
 
 


 
 </body>
</html>

