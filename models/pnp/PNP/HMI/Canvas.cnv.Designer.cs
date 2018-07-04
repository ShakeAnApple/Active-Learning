/*
 * Created by nxtSTUDIO.
 * User: XPMUser
 * Date: 2/8/2012
 * Time: 7:36 PM
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
	/// Summary description for Canvas.
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
			this.View = new NxtStudio.Symbols.PnPView.HMI();
			// 
			// View
			// 
			this.View.BeginInit();
			this.View.AngleIgnore = false;
			this.View.DesignTransformation = new NxtControl.Drawing.Matrix(1D, 0D, 0D, 1D, 159D, 144D);
			this.View.Name = "View";
			this.View.SecurityToken = ((uint)(4294967295u));
			this.View.TagName = "9D2DDF960F18FEC8";
			this.View.EndInit();
			// 
			// Canvas
			// 
			this.Bounds = new NxtControl.Drawing.RectF(((float)(0D)), ((float)(0D)), ((float)(1280D)), ((float)(900D)));
			this.Name = "Canvas";
			this.Shapes.AddRange(new System.ComponentModel.IComponent[] {
									this.View});
			this.Size = new System.Drawing.Size(1280, 900);
		}
		private NxtStudio.Symbols.PnPView.HMI View;
		#endregion
	}
}
