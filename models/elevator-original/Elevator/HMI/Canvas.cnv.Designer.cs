/*
 * Created by nxtSTUDIO.
 * User: Andrei
 * Date: 6/3/2016
 * Time: 4:10 PM
 * 
 */
using System;
using System.ComponentModel;
using System.Collections;
using System.Diagnostics;

using NxtControl.GuiFramework;

namespace HMI.Main.Canvases
{
	/// <summary>
	/// Summary description for Canvas2.
	/// </summary>
	partial class Canvas
	{
		#region Component Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.sDefault1 = new HMI.Main.Symbols.Lift.sDefault();
			this.sDefault2 = new HMI.Main.Symbols.ReqPanelLift.sDefault();
			this.sDefault3 = new HMI.Main.Symbols.Door.sDefault();
			this.sDefault4 = new HMI.Main.Symbols.Door.sDefault();
			this.sDefault5 = new HMI.Main.Symbols.Door.sDefault();
			this.sDefault6 = new HMI.Main.Symbols.CallButton.sDefault();
			this.sDefault7 = new HMI.Main.Symbols.CallButton.sDefault();
			this.sDefault8 = new HMI.Main.Symbols.CallButton.sDefault();
			this.rectangle1 = new NxtControl.GuiFramework.Rectangle();
			this.rectangle2 = new NxtControl.GuiFramework.Rectangle();
			this.rectangle3 = new NxtControl.GuiFramework.Rectangle();
			this.polygon1 = new NxtControl.GuiFramework.Polygon();
			this.polygon2 = new NxtControl.GuiFramework.Polygon();
			this.polygon3 = new NxtControl.GuiFramework.Polygon();
			// 
			// sDefault1
			// 
			this.sDefault1.BeginInit();
			this.sDefault1.AngleIgnore = false;
			this.sDefault1.DesignTransformation = new NxtControl.Drawing.Matrix(1D, 0D, 0D, 1D, 380D, 30D);
			this.sDefault1.Name = "sDefault1";
			this.sDefault1.SecurityToken = ((uint)(4294967295u));
			this.sDefault1.TagName = "8E400D5776E91C44.FB1.FB1";
			this.sDefault1.EndInit();
			// 
			// sDefault2
			// 
			this.sDefault2.BeginInit();
			this.sDefault2.AngleIgnore = false;
			this.sDefault2.DesignTransformation = new NxtControl.Drawing.Matrix(1D, 0D, 0D, 1D, 57D, 172D);
			this.sDefault2.Name = "sDefault2";
			this.sDefault2.SecurityToken = ((uint)(4294967295u));
			this.sDefault2.TagName = "8E400D5776E91C44.FB1.FB3";
			this.sDefault2.EndInit();
			// 
			// sDefault3
			// 
			this.sDefault3.BeginInit();
			this.sDefault3.AngleIgnore = false;
			this.sDefault3.DesignTransformation = new NxtControl.Drawing.Matrix(1D, 0D, 0D, 1D, 377D, 449D);
			this.sDefault3.Name = "sDefault3";
			this.sDefault3.SecurityToken = ((uint)(4294967295u));
			this.sDefault3.TagName = "8E400D5776E91C44.FB1.Doors.Door0";
			this.sDefault3.EndInit();
			// 
			// sDefault4
			// 
			this.sDefault4.BeginInit();
			this.sDefault4.AngleIgnore = false;
			this.sDefault4.DesignTransformation = new NxtControl.Drawing.Matrix(1D, 0D, 0D, 1D, 377D, 255D);
			this.sDefault4.Name = "sDefault4";
			this.sDefault4.SecurityToken = ((uint)(4294967295u));
			this.sDefault4.TagName = "8E400D5776E91C44.FB1.Doors.Door1";
			this.sDefault4.EndInit();
			// 
			// sDefault5
			// 
			this.sDefault5.BeginInit();
			this.sDefault5.AngleIgnore = false;
			this.sDefault5.DesignTransformation = new NxtControl.Drawing.Matrix(1D, 0D, 0D, 1D, 377D, 60D);
			this.sDefault5.Name = "sDefault5";
			this.sDefault5.SecurityToken = ((uint)(4294967295u));
			this.sDefault5.TagName = "8E400D5776E91C44.FB1.Doors.Door2";
			this.sDefault5.EndInit();
			// sDefault6
			// 
			this.sDefault6.BeginInit();
			this.sDefault6.AngleIgnore = false;
			this.sDefault6.DesignTransformation = new NxtControl.Drawing.Matrix(1D, 0D, 0D, 1D, 488D, 491D);
			this.sDefault6.Name = "sDefault6";
			this.sDefault6.SecurityToken = ((uint)(4294967295u));
			this.sDefault6.TagName = "8E400D5776E91C44.FB1.CallButtons.Button0";
			this.sDefault6.EndInit();
			// 
			// sDefault7
			// 
			this.sDefault7.BeginInit();
			this.sDefault7.AngleIgnore = false;
			this.sDefault7.DesignTransformation = new NxtControl.Drawing.Matrix(1D, 0D, 0D, 1D, 488D, 297D);
			this.sDefault7.Name = "sDefault7";
			this.sDefault7.SecurityToken = ((uint)(4294967295u));
			this.sDefault7.TagName = "8E400D5776E91C44.FB1.CallButtons.Button1";
			this.sDefault7.EndInit();
			// 
			// sDefault8
			// 
			this.sDefault8.BeginInit();
			this.sDefault8.AngleIgnore = false;
			this.sDefault8.DesignTransformation = new NxtControl.Drawing.Matrix(1D, 0D, 0D, 1D, 488D, 103D);
			this.sDefault8.Name = "sDefault8";
			this.sDefault8.SecurityToken = ((uint)(4294967295u));
			this.sDefault8.TagName = "8E400D5776E91C44.FB1.CallButtons.Button2";
			this.sDefault8.EndInit();
			// 
			// rectangle1
			// 
			this.rectangle1.Bounds = new NxtControl.Drawing.RectF(((float)(0D)), ((float)(569D)), ((float)(600D)), ((float)(21D)));
			this.rectangle1.Brush = new NxtControl.Drawing.Brush(new NxtControl.Drawing.Color(((byte)(234)), ((byte)(22)), ((byte)(30))));
			this.rectangle1.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.rectangle1.Name = "rectangle1";
			// 
			// rectangle2
			// 
			this.rectangle2.Bounds = new NxtControl.Drawing.RectF(((float)(271D)), ((float)(180D)), ((float)(329D)), ((float)(18D)));
			this.rectangle2.Brush = new NxtControl.Drawing.Brush(new NxtControl.Drawing.Color(((byte)(234)), ((byte)(22)), ((byte)(30))));
			this.rectangle2.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.rectangle2.Name = "rectangle2";
			// 
			// rectangle3
			// 
			this.rectangle3.Bounds = new NxtControl.Drawing.RectF(((float)(271D)), ((float)(375D)), ((float)(329D)), ((float)(18D)));
			this.rectangle3.Brush = new NxtControl.Drawing.Brush(new NxtControl.Drawing.Color(((byte)(234)), ((byte)(22)), ((byte)(30))));
			this.rectangle3.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.rectangle3.Name = "rectangle3";
			// 
			// polygon1
			// 
			this.polygon1.Bounds = new NxtControl.Drawing.RectF(((float)(271D)), ((float)(0D)), ((float)(329D)), ((float)(180D)));
			this.polygon1.Closed = true;
			this.polygon1.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.polygon1.Name = "polygon1";
			this.polygon1.Points.AddRange(new NxtControl.Drawing.PointF[] {
									new NxtControl.Drawing.PointF(271D, 180D),
									new NxtControl.Drawing.PointF(375D, 180D),
									new NxtControl.Drawing.PointF(375D, 59D),
									new NxtControl.Drawing.PointF(481D, 59D),
									new NxtControl.Drawing.PointF(481D, 180D),
									new NxtControl.Drawing.PointF(600D, 180D),
									new NxtControl.Drawing.PointF(600D, 0D),
									new NxtControl.Drawing.PointF(271D, 0D),
									new NxtControl.Drawing.PointF(271D, 180D),
									new NxtControl.Drawing.PointF(271D, 180D)});
			// 
			// polygon2
			// 
			this.polygon2.Bounds = new NxtControl.Drawing.RectF(((float)(271D)), ((float)(196D)), ((float)(329D)), ((float)(180D)));
			this.polygon2.Closed = true;
			this.polygon2.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.polygon2.Name = "polygon2";
			this.polygon2.Points.AddRange(new NxtControl.Drawing.PointF[] {
									new NxtControl.Drawing.PointF(271D, 376D),
									new NxtControl.Drawing.PointF(375D, 376D),
									new NxtControl.Drawing.PointF(375D, 255D),
									new NxtControl.Drawing.PointF(481D, 255D),
									new NxtControl.Drawing.PointF(481D, 376D),
									new NxtControl.Drawing.PointF(600D, 376D),
									new NxtControl.Drawing.PointF(600D, 196D),
									new NxtControl.Drawing.PointF(271D, 196D),
									new NxtControl.Drawing.PointF(271D, 376D),
									new NxtControl.Drawing.PointF(271D, 376D),
									new NxtControl.Drawing.PointF(271D, 376D),
									new NxtControl.Drawing.PointF(271D, 376D),
									new NxtControl.Drawing.PointF(271D, 376D)});
			// 
			// polygon3
			// 
			this.polygon3.Bounds = new NxtControl.Drawing.RectF(((float)(271D)), ((float)(391D)), ((float)(329D)), ((float)(180D)));
			this.polygon3.Closed = true;
			this.polygon3.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.polygon3.Name = "polygon3";
			this.polygon3.Points.AddRange(new NxtControl.Drawing.PointF[] {
									new NxtControl.Drawing.PointF(271D, 571D),
									new NxtControl.Drawing.PointF(375D, 571D),
									new NxtControl.Drawing.PointF(375D, 450D),
									new NxtControl.Drawing.PointF(481D, 450D),
									new NxtControl.Drawing.PointF(481D, 571D),
									new NxtControl.Drawing.PointF(600D, 571D),
									new NxtControl.Drawing.PointF(600D, 391D),
									new NxtControl.Drawing.PointF(271D, 391D),
									new NxtControl.Drawing.PointF(271D, 571D),
									new NxtControl.Drawing.PointF(271D, 571D),
									new NxtControl.Drawing.PointF(271D, 571D),
									new NxtControl.Drawing.PointF(271D, 571D)});
			// 
			// Canvas
			// 
			this.Bounds = new NxtControl.Drawing.RectF(((float)(0D)), ((float)(0D)), ((float)(600D)), ((float)(590D)));
			this.Brush = new NxtControl.Drawing.Brush("CanvasBrush");
			this.Name = "Canvas";
			this.Shapes.AddRange(new System.ComponentModel.IComponent[] {
									this.sDefault1,
									this.sDefault2,
									this.sDefault3,
									this.sDefault4,
									this.polygon2,
									this.sDefault7,
									this.polygon3,
									this.sDefault6,
									this.rectangle1,
									this.sDefault5,
									this.polygon1,
									this.sDefault8,
									this.rectangle2,
									this.rectangle3});
			this.Size = new System.Drawing.Size(600, 590);
		}
		private NxtControl.GuiFramework.Polygon polygon1;
		private NxtControl.GuiFramework.Polygon polygon2;
		private NxtControl.GuiFramework.Polygon polygon3;
		private NxtControl.GuiFramework.Rectangle rectangle1;
		private NxtControl.GuiFramework.Rectangle rectangle2;
		private NxtControl.GuiFramework.Rectangle rectangle3;
		private HMI.Main.Symbols.Lift.sDefault sDefault1;
		private HMI.Main.Symbols.ReqPanelLift.sDefault sDefault2;
		private HMI.Main.Symbols.Door.sDefault sDefault3;
		private HMI.Main.Symbols.Door.sDefault sDefault4;
		private HMI.Main.Symbols.Door.sDefault sDefault5;
		private HMI.Main.Symbols.CallButton.sDefault sDefault6;
		private HMI.Main.Symbols.CallButton.sDefault sDefault7;
		private HMI.Main.Symbols.CallButton.sDefault sDefault8;
		#endregion
	}
}
