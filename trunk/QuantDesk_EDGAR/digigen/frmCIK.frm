VERSION 5.00
Object = "{EAB22AC0-30C1-11CF-A7EB-0000C05BAE0B}#1.1#0"; "shdocvw.dll"
Object = "{F9043C88-F6F2-101A-A3C9-08002B2F49FB}#1.2#0"; "comdlg32.ocx"
Begin VB.Form Form1 
   Caption         =   "Get Data"
   ClientHeight    =   8415
   ClientLeft      =   60
   ClientTop       =   450
   ClientWidth     =   11925
   LinkTopic       =   "Form1"
   ScaleHeight     =   8415
   ScaleWidth      =   11925
   StartUpPosition =   3  'Windows Default
   Begin VB.Frame Frame3 
      Caption         =   "Output FIle"
      Height          =   1455
      Left            =   360
      TabIndex        =   2
      Top             =   6600
      Width           =   11175
      Begin VB.Label Label1 
         Alignment       =   2  'Center
         BeginProperty Font 
            Name            =   "MS Sans Serif"
            Size            =   9.75
            Charset         =   0
            Weight          =   700
            Underline       =   -1  'True
            Italic          =   0   'False
            Strikethrough   =   0   'False
         EndProperty
         ForeColor       =   &H00FF0000&
         Height          =   975
         Left            =   240
         MouseIcon       =   "frmCIK.frx":0000
         MousePointer    =   99  'Custom
         TabIndex        =   8
         Top             =   360
         Width           =   10575
      End
   End
   Begin VB.Frame Frame2 
      Caption         =   "Get Data"
      Height          =   5175
      Left            =   360
      TabIndex        =   1
      Top             =   1320
      Width           =   11175
      Begin MSComDlg.CommonDialog CommonDialog1 
         Left            =   9360
         Top             =   3960
         _ExtentX        =   847
         _ExtentY        =   847
         _Version        =   393216
      End
      Begin VB.CommandButton Command1 
         Caption         =   "Get Data"
         Height          =   495
         Left            =   9720
         TabIndex        =   5
         Top             =   480
         Width           =   1335
      End
      Begin SHDocVwCtl.WebBrowser WebBrowser1 
         Height          =   3735
         Left            =   120
         TabIndex        =   4
         Top             =   1200
         Width           =   10935
         ExtentX         =   19288
         ExtentY         =   6588
         ViewMode        =   0
         Offline         =   0
         Silent          =   0
         RegisterAsBrowser=   0
         RegisterAsDropTarget=   1
         AutoArrange     =   0   'False
         NoClientEdge    =   0   'False
         AlignLeft       =   0   'False
         NoWebView       =   0   'False
         HideFileNames   =   0   'False
         SingleClick     =   0   'False
         SingleSelection =   0   'False
         NoFolders       =   0   'False
         Transparent     =   0   'False
         ViewID          =   "{0057D0E0-3573-11CF-AE69-08002B2E1262}"
         Location        =   ""
      End
      Begin VB.TextBox Text1 
         Appearance      =   0  'Flat
         Height          =   495
         Left            =   120
         TabIndex        =   3
         Top             =   480
         Width           =   9495
      End
   End
   Begin VB.Frame Frame1 
      Caption         =   "Input File"
      Height          =   855
      Left            =   360
      TabIndex        =   0
      Top             =   360
      Width           =   11175
      Begin VB.CommandButton Command2 
         Height          =   495
         Left            =   9960
         Picture         =   "frmCIK.frx":030A
         Style           =   1  'Graphical
         TabIndex        =   7
         Top             =   240
         Width           =   975
      End
      Begin VB.TextBox Text2 
         Appearance      =   0  'Flat
         Height          =   525
         Left            =   120
         TabIndex        =   6
         Top             =   210
         Width           =   9735
      End
   End
   Begin VB.Image Image1 
      Appearance      =   0  'Flat
      BorderStyle     =   1  'Fixed Single
      Height          =   7935
      Index           =   1
      Left            =   240
      Top             =   240
      Width           =   11415
   End
   Begin VB.Image Image1 
      Appearance      =   0  'Flat
      BorderStyle     =   1  'Fixed Single
      Height          =   8175
      Index           =   0
      Left            =   120
      Top             =   120
      Width           =   11655
   End
End
Attribute VB_Name = "Form1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Dim sword As String
Dim Home_URL As String
Dim type_array(100) As String
Dim type_counter As Integer
Dim Process_Active As Boolean
Dim search_counter As Long
Dim search_array(1000) As String
Dim log_file As String
Dim timer_time As Date
Dim timer_date As Date
Dim Vnumruns As Long
Dim first_time As Boolean
Dim Manual_Overide As Boolean
Dim COmpany_Name As String
Dim CIK As String
Dim Company_name1 As String

Dim end_now As Boolean
Dim cookie_file As String
Dim input_file_name As String
Dim Ticker_Symbol As String
Dim CIK_Record As String


Private Sub Command1_Click()



input_file_name = Trim(Text2.Text)

If input_file_name = "" Then
    MsgBox ("Please select or enter the input file path and name")
    Exit Sub
End If

If Dir(input_file_name) = "" Then
    MsgBox ("The Input file that is specified could not be found")
    Exit Sub
End If


Command1.Enabled = False

    'file1 = App.Path & "\test.txt"
    
    outfile = App.Path & "\output.txt"
    
       
    Open input_file_name For Input As #1
    Open outfile For Output As #2
    Line Input #1, inrec
    Print #2, inrec
    
    Record_Counter = 0
    Dim second_try As Boolean
    
        
    
    Do While EOF(1) <> True
        second_try = False
        Line Input #1, inrec
        If InStr(1, inrec, "NULL", vbTextCompare) > 0 Then
        
            ipos = InStr(1, inrec, ",")
            Ipos1 = InStr(ipos + 1, inrec, """")
            Ipos2 = InStr(Ipos1 + 1, inrec, """")
                    
            Ticker_Symbol = Mid(inrec, Ipos1 + 1, Ipos2 - Ipos1 - 1)
        
            outrec = Mid(inrec, 1, InStr(1, inrec, "NULL", vbTextCompare) - 1)
            
            inrec = Trim(inrec)
            If inrec <> "" Then
                ipos = InStr(2, inrec, """")
                Ticker_Symbol = Mid(inrec, 2, ipos - 2)
                
                Ipos1 = InStr(ipos + 1, inrec, """")
                Ipos2 = InStr(Ipos1 + 1, inrec, """")
                COmpany_Name = Mid(inrec, Ipos1 + 1, Ipos2 - Ipos1 - 1)
                
                
                   msg1 = "Total Number Of Records Processed ==> " & Record_Counter & " . " & vbCrLf & _
            " Current Ticker Symbol ==> " & Ticker_Symbol & " And Current Company  Symbol ==> " & COmpany_Name
       Label1.Caption = msg1
                
                Company_name1 = COmpany_Name
                'Print #2, COmpany_Name
                GetCIK
                If CIK = "NULL" Then
                    If InStr(COmpany_Name, " ") > 0 Then
                        Company_name1 = Mid(COmpany_Name, 1, InStr(COmpany_Name, " ") - 1)
                        GetCIK
                        second_try = True
                    End If
                End If
                Record_Counter = Record_Counter + 1
            End If
            '''outrec = outrec & """" & CIK & """"
            outrec = outrec & CIK_Record
            'If second_try = True Then
            '    outrec = outrec & "," & "CHECK"
            '    second_try = False
            'End If
            
            'Print #2, outrec
        Else
            outrec = inrec
            Record_Counter = Record_Counter + 1
        End If
        Print #2, outrec
        CIK = ""
        
            msg1 = "Total Number Of Records Processed ==> " & Record_Counter & " . " & vbCrLf & _
            " Current Ticker Symbol ==> " & Ticker_Symbol & " And Current Company  Symbol ==> " & COmpany_Name
       Label1.Caption = msg1
        
        
        
       ' If Record_Counter > 5 Then
       '     Exit Do
       ' End If
        
      '   If Record_Counter > 5000 Then
       '     Exit Do
       ' End If
       

       DoEvents
        
    Loop
    
    Close #1
    Close #2
    
    Label1.Caption = outfile
    
    Open cookie_file For Output As #1
    Print #1, input_file_name
    Close #1
    
    tt = Record_Counter & " Records Processed."
    aa = MsgBox(tt, vbExclamation, "Processing Complete !!")
    
    Command1.Enabled = True

End Sub




Private Sub GetCIK()
    On Error Resume Next
    Company_name1 = Replace(Company_name1, " ", "+")
    URL1 = "http://sec.gov/cgi-bin/browse-edgar?company=&CIK=" & Ticker_Symbol & "&filenum=&State=&SIC=&owner=include&action=getcompany"
    URL2 = "http://www.secform4.com/insider-trading/" & Ticker_Symbol & ".htm"
    URL3 = "http://www.secinfo.com/$/Search.asp?Find=" & Ticker_Symbol
    URL4 = "http://sec.gov/cgi-bin/browse-edgar?company=" & Company_name1 & "&CIK=&filenum=&State=&SIC=&owner=include&action=getcompany"
    
    Text1.Text = URL1

    Err.Number = 0
    WebBrowser1.Navigate (Text1.Text)
     Do While WebBrowser1.ReadyState <> READYSTATE_COMPLETE
        If Err.Number <> 0 Then
            Exit Do
        End If
        DoEvents
        If end_now = True Then
            End
        End If
    
    Loop
    
    tt = WebBrowser1.Document.Body.innerHTML
    ipos = InStr(1, tt, "cik=", vbTextCompare)
    If ipos > 1 Then
        Ipos1 = InStr(ipos + 1, tt, "=", vbTextCompare)
        Ipos2 = InStr(Ipos1 + 1, tt, "&", vbTextCompare)
        len1 = Ipos2 - Ipos1
        CIK = Mid(tt, Ipos1 + 1, len1 - 1)
    Else
        CIK = "NULL"
    End If
    CIK1 = CIK
    
    '==========================
    '==========================
    '''If CIK <> "NULL" Then
        '''Exit Sub
    '''End If
    
        
    '==========================
    
    Text1.Text = URL2
       
    Err.Number = 0
    WebBrowser1.Navigate (Text1.Text)
     Do While WebBrowser1.ReadyState <> READYSTATE_COMPLETE
        If Err.Number <> 0 Then
            Exit Do
        End If
        DoEvents
        If end_now = True Then
            End
        End If

    Loop
    
    tt = WebBrowser1.Document.Body.innerHTML
    ipos = InStr(1, tt, "cik=", vbTextCompare)
    If ipos > 1 Then
        Ipos1 = InStr(ipos + 1, tt, "=", vbTextCompare)
        Ipos2 = InStr(Ipos1 + 1, tt, "&", vbTextCompare)
        len1 = Ipos2 - Ipos1
        CIK = Mid(tt, Ipos1 + 1, len1 - 1)
    Else
        CIK = "NULL"
    End If
    
    CIK2 = CIK
    
    '==========================
    '==========================
    '''If CIK <> "NULL" Then
       ''' Exit Sub
    '''End If
        
    '==========================
    '==========================
    
    
    '==========================
    Text1.Text = URL3
       
    Err.Number = 0
    WebBrowser1.Navigate (Text1.Text)
     Do While WebBrowser1.ReadyState <> READYSTATE_COMPLETE
        If Err.Number <> 0 Then
            Exit Do
        End If
        DoEvents
        If end_now = True Then
            End
        End If
  
    Loop
    
    tt = WebBrowser1.Document.Body.innerHTML
    ipos = InStr(1, tt, "cik=", vbTextCompare)
    If ipos > 1 Then
        Ipos1 = InStr(ipos + 1, tt, "=", vbTextCompare)
        Ipos2 = InStr(Ipos1 + 1, tt, " ", vbTextCompare)
        len1 = Ipos2 - Ipos1
        CIK = Mid(tt, Ipos1 + 1, len1 - 1)
        'e.g of output  790500">Aberdeen
        CIK = Mid(CIK, 1, InStr(1, CIK, """") - 1)
    Else
        CIK = "NULL"
    End If
    
    CIK3 = CIK
    
    '==========================
    '==========================
    '''If CIK <> "NULL" Then
       ''' Exit Sub
    '''End If
        
    '==========================
    '==========================
    '==========================
    '==========================
    Text1.Text = URL4

    Err.Number = 0
    WebBrowser1.Navigate (Text1.Text)
     Do While WebBrowser1.ReadyState <> READYSTATE_COMPLETE
        If Err.Number <> 0 Then
            Exit Do
        End If
        DoEvents
        If end_now = True Then
            End
        End If
    
    Loop
    
    tt = WebBrowser1.Document.Body.innerHTML
    ipos = InStr(1, tt, "cik=", vbTextCompare)
    If ipos > 1 Then
        Ipos1 = InStr(ipos + 1, tt, "=", vbTextCompare)
        Ipos2 = InStr(Ipos1 + 1, tt, "&", vbTextCompare)
        len1 = Ipos2 - Ipos1
        CIK = Mid(tt, Ipos1 + 1, len1 - 1)
    Else
        CIK = "NULL"
    End If
    CIK4 = CIK
    
    CIK_Record = ",""" & CIK1 & ",""" & URL1 & """" & _
                    ",""" & CIK2 & """,""" & URL2 & """" & _
                    ",""" & CIK3 & """,""" & URL3 & """" & _
                    ",""" & CIK4 & """,""" & URL4 & """"

End Sub



Private Sub Form_Load()
    cookie_file = App.Path & "\cookie.path"
    
    On Error Resume Next
    Open cookie_file For Input As #1
    Line Input #1, input_file_name
    Close #1
    
    Text2.Text = input_file_name
    On Error GoTo 0

End Sub

Private Sub Form_Unload(Cancel As Integer)

    Open cookie_file For Output As #5
    Print #5, input_file_name
    Close #5

    end_now = True
End Sub

Private Sub Label1_Click()

On Error Resume Next

Dim Command1 As String

    Command1 = Label1.Caption
    Command1 = "notepad " & Command1
  Shell (Command1)
End Sub





