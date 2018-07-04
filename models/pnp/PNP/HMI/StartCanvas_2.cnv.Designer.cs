/*
 * Created by nxtStudio.
 * User: kovaivo
 * Date: 18.09.2008
 * Time: 17:50
 * 
 * To change this template use Tools | Options | Coding | Edit Standard Headers.
 */
using System;
using System.ComponentModel;
using System.Collections;
using System.Diagnostics;

using NxtControl.GuiFramework;

namespace NxtStudio.Canvases
{
  /// <summary>
  /// Summary description for StartCanvas_2.
  /// </summary>
  partial class StartCanvas_2
  {
    #region Component Designer generated code
    /// <summary>
    /// Required method for Designer support - do not modify
    /// the contents of this method with the code editor.
    /// </summary>
    private void InitializeComponent()
    {
      System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(StartCanvas_2));
      this.logo = new NxtControl.GuiFramework.Rectangle();
      this.canvasTopologyRose = new NxtControl.GuiFramework.CanvasTopologyRose();      
      this.canvasTopologySeparator1 = new NxtControl.GuiFramework.CanvasTopologySeparator();      
      this.canvasTopologySeparator2 = new NxtControl.GuiFramework.CanvasTopologySeparator();      
      this.workArea = new NxtControl.GuiFramework.WorkAreaControl();
      
			this.header1 = new NxtControl.GuiFramework.Rectangle();
      this.header2 = new NxtControl.GuiFramework.Rectangle();
this.siblingsPanel = new NxtControl.GuiFramework.CanvasTopologyPanel();
this.childrenPanel = new NxtControl.GuiFramework.CanvasTopologyPanel();
this.exit1 = new NxtControl.GuiFramework.Exit();
this.login1 = new NxtControl.GuiFramework.Login();
this.language1 = new NxtControl.GuiFramework.LanguageSwitcher();
this.currentUser1 = new NxtControl.GuiFramework.CurrentUser();

      // 
      // logo
      //
      this.logo.Anchor = NxtControl.Drawing.AnchorStyles.Left; 
      this.logo.Bounds = new NxtControl.Drawing.RectF(((float)(8)), ((float)(4)), ((float)(123)), ((float)(72)));
      this.logo.Brush = new NxtControl.Drawing.Brush(new NxtControl.Drawing.Color("Transparent"));
      this.logo.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
      this.logo.ImageStream = ((System.IO.MemoryStream)(resources.GetObject("logo.ImageStream")));
      this.logo.Name = "logo";
      this.logo.Pen = new NxtControl.Drawing.Pen(new NxtControl.Drawing.Color("Transparent"), 1F, NxtControl.Drawing.DashStyle.Solid);
      this.logo.TextColor = new NxtControl.Drawing.Color("Black");
      // 
      // canvasTopologySeparator1
      //
      this.canvasTopologySeparator1.Anchor = NxtControl.Drawing.AnchorStyles.Left; 
      this.canvasTopologySeparator1.Bounds = new NxtControl.Drawing.RectF(((float)(135)), ((float)(4)), ((float)(3)), ((float)(76)));
      this.canvasTopologySeparator1.Name = "canvasTopologySeparator1";
      this.canvasTopologySeparator1.LookAndFeel = "Normal";
      this.canvasTopologySeparator1.Visible = false;
      // 
      // canvasTopologySeparator2
      //
      this.canvasTopologySeparator2.Anchor = NxtControl.Drawing.AnchorStyles.Left; 
      this.canvasTopologySeparator2.Bounds = new NxtControl.Drawing.RectF(((float)(1152)), ((float)(4)), ((float)(3)), ((float)(76)));
      this.canvasTopologySeparator2.Name = "canvasTopologySeparator2";
      this.canvasTopologySeparator2.LookAndFeel = "Normal";
      this.canvasTopologySeparator2.Visible = false;
      // 
      // canvasTopologyRose
      // 
      this.canvasTopologyRose.BeginInit();
      this.canvasTopologyRose.Bounds = new NxtControl.Drawing.RectF(((float)(143)), ((float)(4)), ((float)(72)), ((float)(72)));
      this.canvasTopologyRose.Color = new NxtControl.Drawing.Color((byte)78, (byte)106, (byte)150);
      this.canvasTopologyRose.Name = "canvasTopologyRose";
      this.canvasTopologyRose.Transformation = new NxtControl.Drawing.Matrix(0.791208791208791, 0, 0, 0.827586206896552, 136.67032967033, 2.3448275862069);
      this.canvasTopologyRose.LookAndFeel = "Normal";
      this.canvasTopologyRose.EndInit();
      // 
      // workArea
      // 
      this.workArea.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
                  | System.Windows.Forms.AnchorStyles.Left) 
                  | System.Windows.Forms.AnchorStyles.Right)));
      this.workArea.AutoScroll = true;
      this.workArea.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
      this.workArea.Location = new System.Drawing.Point(0, 80);
      this.workArea.Name = "workArea";
      this.workArea.RuntimeMode = true;
      this.workArea.Size = new System.Drawing.Size(1280, 900);
      this.workArea.TabIndex = 0;
      this.workArea.BackColor = System.Drawing.Color.FromArgb(255, 255, 255);
      
      // 
      // header1
      // 
      this.header1.Anchor = ((NxtControl.Drawing.AnchorStyles)((NxtControl.Drawing.AnchorStyles.Left | NxtControl.Drawing.AnchorStyles.Right)));
      this.header1.Bounds = new NxtControl.Drawing.RectF(((float)(0)), ((float)(39)), ((float)(1280)), ((float)(40)));
      this.header1.Brush = new NxtControl.Drawing.Brush(new NxtControl.Drawing.Color((byte)78, (byte)78, (byte)78), new NxtControl.Drawing.GradientFill(NxtControl.Drawing.GradientFillOrientation.VerticalBottom, NxtControl.Drawing.GradientFillBrightness.Light));
      this.header1.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
      this.header1.Name = "header1";
      this.header1.Pen = new NxtControl.Drawing.Pen(new NxtControl.Drawing.Color("Transparent"), 0F, NxtControl.Drawing.DashStyle.Solid);
      this.header1.TextColor = new NxtControl.Drawing.Color("Black");
      // 
      // header2
      // 
      this.header2.Anchor = ((NxtControl.Drawing.AnchorStyles)((NxtControl.Drawing.AnchorStyles.Left | NxtControl.Drawing.AnchorStyles.Right)));
      this.header2.Bounds = new NxtControl.Drawing.RectF(((float)(0)), ((float)(0)), ((float)(1280)), ((float)(40)));
      this.header2.Brush = new NxtControl.Drawing.Brush(new NxtControl.Drawing.Color((byte)78, (byte)78, (byte)78), new NxtControl.Drawing.GradientFill(NxtControl.Drawing.GradientFillOrientation.VerticalTop, NxtControl.Drawing.GradientFillBrightness.Light));
      this.header2.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
      this.header2.Name = "header2";
      this.header2.Pen = new NxtControl.Drawing.Pen(new NxtControl.Drawing.Color("Transparent"), 0F, NxtControl.Drawing.DashStyle.Solid);
      this.header2.TextColor = new NxtControl.Drawing.Color("Black");
    //
    // siblingsPanel
    //
    this.siblingsPanel.BeginInit();
    this.siblingsPanel.Anchor = ((NxtControl.Drawing.AnchorStyles)((NxtControl.Drawing.AnchorStyles.Left | NxtControl.Drawing.AnchorStyles.Right)));
    this.siblingsPanel.Bounds = new NxtControl.Drawing.RectF(((float)(227)), ((float)(0)), ((float)(925)), ((float)(80)));
    this.siblingsPanel.BtnColor = new NxtControl.Drawing.Color((byte)78, (byte)106, (byte)150);
    this.siblingsPanel.CurrentBtnColor = new NxtControl.Drawing.Color((byte)78, (byte)106, (byte)150);
    this.siblingsPanel.TopologyType = NxtControl.GuiFramework.CanvasTopologyType.Sibling;
    this.siblingsPanel.LookAndFeel = "Normal";
    this.siblingsPanel.BtnHeight = 30;
    this.siblingsPanel.BtnWidth = 140;
    this.siblingsPanel.Name = "siblingsPanel";
    this.siblingsPanel.WorkArea = workArea;
    this.siblingsPanel.Orientation = System.Windows.Forms.Orientation.Horizontal;
    this.siblingsPanel.EndInit();
    //
    // childrenPanel
    //
    this.childrenPanel.BeginInit();
    this.childrenPanel.Anchor = ((NxtControl.Drawing.AnchorStyles)((NxtControl.Drawing.AnchorStyles.Left | NxtControl.Drawing.AnchorStyles.Right)));
    this.childrenPanel.Bounds = new NxtControl.Drawing.RectF(((float)(227)), ((float)(40)), ((float)(925)), ((float)(80)));
    this.childrenPanel.BtnColor = new NxtControl.Drawing.Color((byte)78, (byte)106, (byte)150);
    this.childrenPanel.CurrentBtnColor = new NxtControl.Drawing.Color((byte)78, (byte)106, (byte)150);
    this.childrenPanel.TopologyType = NxtControl.GuiFramework.CanvasTopologyType.Child;
    this.childrenPanel.LookAndFeel = "Normal";
    this.childrenPanel.BtnHeight = 30;
    this.childrenPanel.BtnWidth = 140;
    this.childrenPanel.Name = "childrenPanel";
    this.childrenPanel.WorkArea = workArea;
    this.childrenPanel.Orientation = System.Windows.Forms.Orientation.Horizontal;
    this.childrenPanel.EndInit();
			// 
			// exit1
			// 
      this.exit1.Anchor = NxtControl.Drawing.AnchorStyles.Right;
			this.exit1.Bounds = new NxtControl.Drawing.RectF(((float)(1240)), ((float)(4)), ((float)(36)), ((float)(36)));
      this.exit1.Text = "";
			this.exit1.LookAndFeel = "Normal";
			this.exit1.Name = "exit1";
			this.exit1.Radius = 0;
			this.exit1.Warning = true;
			this.exit1.WarningText = "";
			// 
			// login1
			// 
      this.login1.Anchor = NxtControl.Drawing.AnchorStyles.Right;
			this.login1.Bounds = new NxtControl.Drawing.RectF(((float)(1160)), ((float)(4)), ((float)(36)), ((float)(36)));
      this.login1.Text = "";
			this.login1.LookAndFeel = "Normal";
			this.login1.Name = "login1";
			this.login1.Radius = 0;
			// 
			// language1
			// 
      this.language1.Anchor = NxtControl.Drawing.AnchorStyles.Right;
			this.language1.Bounds = new NxtControl.Drawing.RectF(((float)(1200)), ((float)(4)), ((float)(36)), ((float)(36)));
			this.language1.LookAndFeel = "Normal";
			this.language1.Name = "language1";
			// 
			// currentUser1
			// 
      this.currentUser1.Anchor = NxtControl.Drawing.AnchorStyles.Right;
			this.currentUser1.AngleIgnore = true;
			this.currentUser1.Bounds = new NxtControl.Drawing.RectF(((float)(1176)), ((float)(44)), ((float)(100)), ((float)(32)));
			this.currentUser1.Brush = new NxtControl.Drawing.Brush();
			this.currentUser1.Font = new NxtControl.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular);
			this.currentUser1.LookAndFeel = "Normal";
			this.currentUser1.Name = "currentUser1";
			this.currentUser1.Pen = new NxtControl.Drawing.Pen(new NxtControl.Drawing.Color("Transparent"), 1F, NxtControl.Drawing.DashStyle.Solid);
			this.currentUser1.Text = "currentUser1";
      this.currentUser1.TextAlignment = NxtControl.Drawing.ContentAlignment.MiddleCenter;
			this.currentUser1.TextColor = new NxtControl.Drawing.Color("White");

      // 
      // StartCanvas_2
      // 
      this.Bounds = new NxtControl.Drawing.RectF(((float)(0)), ((float)(0)), ((float)(1280)), ((float)(980)));
      this.Name = "StartCanvas_2";
      this.ResizeBehavior = NxtControl.GuiFramework.ResizeBehavior.None;
      this.Shapes.AddRange(new System.ComponentModel.IComponent[] {
                  this.header1,
                  this.header2,

                  this.logo,
                  this.canvasTopologyRose,
                        siblingsPanel,
      childrenPanel,
      exit1,
      login1,
      language1,
      currentUser1,

                  this.canvasTopologySeparator1,
				  this.canvasTopologySeparator2,
				  this.workArea});
      this.Size = new System.Drawing.Size(1280, 980);
    }
    
    private NxtControl.GuiFramework.Rectangle header2;
    private NxtControl.GuiFramework.Rectangle header1;
			private NxtControl.GuiFramework.CanvasTopologyPanel siblingsPanel;
			private NxtControl.GuiFramework.CanvasTopologyPanel childrenPanel;
private NxtControl.GuiFramework.CurrentUser currentUser1;
private NxtControl.GuiFramework.Login login1;
private NxtControl.GuiFramework.LanguageSwitcher language1;
private NxtControl.GuiFramework.Exit exit1;

    private NxtControl.GuiFramework.WorkAreaControl workArea;
    private NxtControl.GuiFramework.CanvasTopologyRose canvasTopologyRose;
    private NxtControl.GuiFramework.CanvasTopologySeparator canvasTopologySeparator1;
    private NxtControl.GuiFramework.CanvasTopologySeparator canvasTopologySeparator2;
    private NxtControl.GuiFramework.Rectangle logo;
    #endregion
  }
}
