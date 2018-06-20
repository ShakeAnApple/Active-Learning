/*
 * Created by nxtSTUDIO.
 * User: XPMUser
 * Date: 2/8/2012
 * Time: 11:04 AM
 * 
 * To change this template use Tools | Options | Coding | Edit Standard Headers.
 */
using System;
using System.ComponentModel;
using System.Collections;
using NxtStudio.Symbols;
using NxtControl.GuiFramework;

namespace NxtStudio.Symbols.PnPView
{
	/// <summary>
	/// Summary description for HMI.
	/// </summary>
	partial class HMI
	{

		#region Component Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(HMI));
			this.vac = new NxtControl.GuiFramework.Rectangle();
			this.pnpVC = new NxtControl.GuiFramework.Rectangle();
			this.pnpRC = new NxtControl.GuiFramework.Rectangle();
			this.pnpLC = new NxtControl.GuiFramework.Rectangle();
			this.pnpBase = new NxtControl.GuiFramework.Rectangle();
			this.pp1 = new NxtControl.GuiFramework.Rectangle();
			this.pp2 = new NxtControl.GuiFramework.Rectangle();
			this.pp3 = new NxtControl.GuiFramework.Rectangle();
			this.Slider = new NxtControl.GuiFramework.Rectangle();
			this.wp1 = new NxtControl.GuiFramework.Rectangle();
			this.wp2 = new NxtControl.GuiFramework.Rectangle();
			this.wp3 = new NxtControl.GuiFramework.Rectangle();
			this.rectangle1 = new NxtControl.GuiFramework.Rectangle();
			// 
			// vac
			// 
			this.vac.Bounds = new NxtControl.Drawing.RectF(((float)(360)), ((float)(40)), ((float)(26)), ((float)(91)));
			this.vac.Brush = new NxtControl.Drawing.Brush();
			this.vac.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.vac.ImageStream = ((System.IO.MemoryStream)(resources.GetObject("vac.ImageStream")));
			this.vac.Name = "vac";
			this.vac.Pen = new NxtControl.Drawing.Pen(new NxtControl.Drawing.Color("Transparent"), 0F, NxtControl.Drawing.DashStyle.Solid);
			this.vac.TextColor = new NxtControl.Drawing.Color(((byte)(0)), ((byte)(0)), ((byte)(0)));
			this.vac.Visible = false;
			// 
			// pnpVC
			// 
			this.pnpVC.Bounds = new NxtControl.Drawing.RectF(((float)(307)), ((float)(37)), ((float)(26)), ((float)(101)));
			this.pnpVC.Brush = new NxtControl.Drawing.Brush();
			this.pnpVC.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.pnpVC.ImageStream = ((System.IO.MemoryStream)(resources.GetObject("pnpVC.ImageStream")));
			this.pnpVC.Name = "pnpVC";
			this.pnpVC.Pen = new NxtControl.Drawing.Pen(new NxtControl.Drawing.Color(((byte)(255)), ((byte)(255)), ((byte)(255))), 1F, NxtControl.Drawing.DashStyle.Solid);
			this.pnpVC.TextColor = new NxtControl.Drawing.Color(((byte)(0)), ((byte)(0)), ((byte)(0)));
			// 
			// pnpRC
			// 
			this.pnpRC.Bounds = new NxtControl.Drawing.RectF(((float)(120)), ((float)(32)), ((float)(242)), ((float)(94)));
			this.pnpRC.Brush = new NxtControl.Drawing.Brush();
			this.pnpRC.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.pnpRC.ImageStream = ((System.IO.MemoryStream)(resources.GetObject("pnpRC.ImageStream")));
			this.pnpRC.Name = "pnpRC";
			this.pnpRC.Pen = new NxtControl.Drawing.Pen(new NxtControl.Drawing.Color(((byte)(255)), ((byte)(255)), ((byte)(255))), 1F, NxtControl.Drawing.DashStyle.Solid);
			this.pnpRC.TextColor = new NxtControl.Drawing.Color(((byte)(0)), ((byte)(0)), ((byte)(0)));
			// 
			// pnpLC
			// 
			this.pnpLC.Bounds = new NxtControl.Drawing.RectF(((float)(24)), ((float)(24)), ((float)(273)), ((float)(72)));
			this.pnpLC.Brush = new NxtControl.Drawing.Brush();
			this.pnpLC.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.pnpLC.ImageStream = ((System.IO.MemoryStream)(resources.GetObject("pnpLC.ImageStream")));
			this.pnpLC.Name = "pnpLC";
			this.pnpLC.Pen = new NxtControl.Drawing.Pen(new NxtControl.Drawing.Color(((byte)(255)), ((byte)(255)), ((byte)(255))), 1F, NxtControl.Drawing.DashStyle.Solid);
			this.pnpLC.TextColor = new NxtControl.Drawing.Color(((byte)(0)), ((byte)(0)), ((byte)(0)));
			// 
			// pnpBase
			// 
			this.pnpBase.Bounds = new NxtControl.Drawing.RectF(((float)(5)), ((float)(25)), ((float)(112)), ((float)(205)));
			this.pnpBase.Brush = new NxtControl.Drawing.Brush();
			this.pnpBase.FillDirection = NxtControl.Drawing.FillDirection.TopToDown;
			this.pnpBase.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.pnpBase.ImageStream = ((System.IO.MemoryStream)(resources.GetObject("pnpBase.ImageStream")));
			this.pnpBase.Name = "pnpBase";
			this.pnpBase.Pen = new NxtControl.Drawing.Pen(new NxtControl.Drawing.Color(((byte)(255)), ((byte)(255)), ((byte)(255))), 0F, NxtControl.Drawing.DashStyle.Solid);
			this.pnpBase.TextColor = new NxtControl.Drawing.Color(((byte)(0)), ((byte)(0)), ((byte)(0)));
			// 
			// pp1
			// 
			this.pp1.Bounds = new NxtControl.Drawing.RectF(((float)(376)), ((float)(220)), ((float)(44)), ((float)(13)));
			this.pp1.Brush = new NxtControl.Drawing.Brush();
			this.pp1.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.pp1.ImageStream = ((System.IO.MemoryStream)(resources.GetObject("pp1.ImageStream")));
			this.pp1.Name = "pp1";
			this.pp1.Pen = new NxtControl.Drawing.Pen(new NxtControl.Drawing.Color(((byte)(255)), ((byte)(255)), ((byte)(255))), 1F, NxtControl.Drawing.DashStyle.Solid);
			this.pp1.TextColor = new NxtControl.Drawing.Color(((byte)(0)), ((byte)(0)), ((byte)(0)));
			// 
			// pp2
			// 
			this.pp2.Bounds = new NxtControl.Drawing.RectF(((float)(451)), ((float)(220)), ((float)(44)), ((float)(13)));
			this.pp2.Brush = new NxtControl.Drawing.Brush();
			this.pp2.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.pp2.ImageStream = ((System.IO.MemoryStream)(resources.GetObject("pp2.ImageStream")));
			this.pp2.Name = "pp2";
			this.pp2.Pen = new NxtControl.Drawing.Pen(new NxtControl.Drawing.Color(((byte)(255)), ((byte)(255)), ((byte)(255))), 1F, NxtControl.Drawing.DashStyle.Solid);
			this.pp2.TextColor = new NxtControl.Drawing.Color(((byte)(0)), ((byte)(0)), ((byte)(0)));
			// 
			// pp3
			// 
			this.pp3.Bounds = new NxtControl.Drawing.RectF(((float)(526)), ((float)(220)), ((float)(44)), ((float)(13)));
			this.pp3.Brush = new NxtControl.Drawing.Brush();
			this.pp3.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.pp3.ImageStream = ((System.IO.MemoryStream)(resources.GetObject("pp3.ImageStream")));
			this.pp3.Name = "pp3";
			this.pp3.Pen = new NxtControl.Drawing.Pen(new NxtControl.Drawing.Color(((byte)(255)), ((byte)(255)), ((byte)(255))), 1F, NxtControl.Drawing.DashStyle.Solid);
			this.pp3.TextColor = new NxtControl.Drawing.Color(((byte)(0)), ((byte)(0)), ((byte)(0)));
			// 
			// Slider
			// 
			this.Slider.Bounds = new NxtControl.Drawing.RectF(((float)(301)), ((float)(210)), ((float)(44)), ((float)(126)));
			this.Slider.Brush = new NxtControl.Drawing.Brush();
			this.Slider.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.Slider.ImageStream = ((System.IO.MemoryStream)(resources.GetObject("Slider.ImageStream")));
			this.Slider.Name = "Slider";
			this.Slider.Pen = new NxtControl.Drawing.Pen(new NxtControl.Drawing.Color(((byte)(255)), ((byte)(255)), ((byte)(255))), 1F, NxtControl.Drawing.DashStyle.Solid);
			this.Slider.TextColor = new NxtControl.Drawing.Color(((byte)(0)), ((byte)(0)), ((byte)(0)));
			// 
			// wp1
			// 
			this.wp1.Bounds = new NxtControl.Drawing.RectF(((float)(664)), ((float)(24)), ((float)(36)), ((float)(15)));
			this.wp1.Brush = new NxtControl.Drawing.Brush(new NxtControl.Drawing.Color(((byte)(255)), ((byte)(255)), ((byte)(255))));
			this.wp1.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.wp1.ImageStream = ((System.IO.MemoryStream)(resources.GetObject("wp1.ImageStream")));
			this.wp1.Name = "wp1";
			this.wp1.Pen = new NxtControl.Drawing.Pen(new NxtControl.Drawing.Color(((byte)(0)), ((byte)(0)), ((byte)(0))), 1F, NxtControl.Drawing.DashStyle.Solid);
			this.wp1.TextColor = new NxtControl.Drawing.Color(((byte)(0)), ((byte)(0)), ((byte)(0)));
			// 
			// wp2
			// 
			this.wp2.Bounds = new NxtControl.Drawing.RectF(((float)(664)), ((float)(56)), ((float)(36)), ((float)(15)));
			this.wp2.Brush = new NxtControl.Drawing.Brush(new NxtControl.Drawing.Color(((byte)(255)), ((byte)(255)), ((byte)(255))));
			this.wp2.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.wp2.ImageStream = ((System.IO.MemoryStream)(resources.GetObject("wp2.ImageStream")));
			this.wp2.Name = "wp2";
			this.wp2.Pen = new NxtControl.Drawing.Pen(new NxtControl.Drawing.Color(((byte)(0)), ((byte)(0)), ((byte)(0))), 1F, NxtControl.Drawing.DashStyle.Solid);
			this.wp2.TextColor = new NxtControl.Drawing.Color(((byte)(0)), ((byte)(0)), ((byte)(0)));
			// 
			// wp3
			// 
			this.wp3.Bounds = new NxtControl.Drawing.RectF(((float)(664)), ((float)(88)), ((float)(36)), ((float)(15)));
			this.wp3.Brush = new NxtControl.Drawing.Brush(new NxtControl.Drawing.Color(((byte)(255)), ((byte)(255)), ((byte)(255))));
			this.wp3.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.wp3.ImageStream = ((System.IO.MemoryStream)(resources.GetObject("wp3.ImageStream")));
			this.wp3.Name = "wp3";
			this.wp3.Pen = new NxtControl.Drawing.Pen(new NxtControl.Drawing.Color(((byte)(0)), ((byte)(0)), ((byte)(0))), 1F, NxtControl.Drawing.DashStyle.Solid);
			this.wp3.TextColor = new NxtControl.Drawing.Color(((byte)(0)), ((byte)(0)), ((byte)(0)));
			// 
			// rectangle1
			// 
			this.rectangle1.Bounds = new NxtControl.Drawing.RectF(((float)(0)), ((float)(0)), ((float)(848)), ((float)(376)));
			this.rectangle1.Brush = new NxtControl.Drawing.Brush(new NxtControl.Drawing.Color(((byte)(255)), ((byte)(255)), ((byte)(255))));
			this.rectangle1.Font = new NxtControl.Drawing.Font("Arial", 8F, System.Drawing.FontStyle.Regular);
			this.rectangle1.Name = "rectangle1";
			this.rectangle1.OpenFaceplates.Add(new NxtControl.GuiFramework.OpenFaceplate("Faceplate", NxtControl.GuiFramework.MouseButtonType.DoubleClick));
			this.rectangle1.Pen = new NxtControl.Drawing.Pen(new NxtControl.Drawing.Color(((byte)(0)), ((byte)(0)), ((byte)(0))), 1F, NxtControl.Drawing.DashStyle.Solid);
			this.rectangle1.TextColor = new NxtControl.Drawing.Color(((byte)(0)), ((byte)(0)), ((byte)(0)));
			// 
			// HMI
			// 
			this.Name = "HMI";
			this.OpenFaceplates.Add(new NxtControl.GuiFramework.OpenFaceplate("Faceplate", NxtControl.GuiFramework.MouseButtonType.DoubleClick));
			this.Shapes.AddRange(new System.ComponentModel.IComponent[] {
									this.rectangle1,
									this.pnpVC,
									this.Slider,
									this.pp1,
									this.pp2,
									this.pp3,
									this.wp3,
									this.wp2,
									this.wp1,
									this.pnpRC,
									this.pnpLC,
									this.pnpBase,
									this.vac});
			this.SymbolSize = new System.Drawing.Size(848, 376);
		}
		private NxtControl.GuiFramework.Rectangle rectangle1;
		private NxtControl.GuiFramework.Rectangle wp3;
		private NxtControl.GuiFramework.Rectangle wp2;
		private NxtControl.GuiFramework.Rectangle wp1;
		private NxtControl.GuiFramework.Rectangle Slider;
		private NxtControl.GuiFramework.Rectangle pp3;
		private NxtControl.GuiFramework.Rectangle pp2;
		private NxtControl.GuiFramework.Rectangle pp1;
		private NxtControl.GuiFramework.Rectangle pnpBase;
		private NxtControl.GuiFramework.Rectangle pnpLC;
		private NxtControl.GuiFramework.Rectangle pnpRC;
		private NxtControl.GuiFramework.Rectangle pnpVC;
		private NxtControl.GuiFramework.Rectangle vac;
		#endregion
	}
}
