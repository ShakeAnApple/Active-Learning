/*
 * Created by nxtSTUDIO.
 * User: XPMUser
 * Date: 2/8/2012
 * Time: 7:03 PM
 * 
 * To change this template use Tools | Options | Coding | Edit Standard Headers.
 */
using System;
using System.ComponentModel;
using System.Collections;
using System.Diagnostics;
using NxtControl.GuiFramework;

namespace NxtStudio.Faceplates.PnPView
{
	/// <summary>
	/// Summary description for Faceplate.
	/// </summary>
	partial class Faceplate
	{

		#region Component Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.wp1 = new NxtControl.GuiFramework.CheckBox();
			this.wp2 = new NxtControl.GuiFramework.CheckBox();
			this.wp3 = new NxtControl.GuiFramework.CheckBox();
			this.addWPButton = new NxtControl.GuiFramework.Button();
			// 
			// wp1
			// 
			this.wp1.Location = new System.Drawing.Point(56, 24);
			this.wp1.Name = "wp1";
			this.wp1.Size = new System.Drawing.Size(88, 24);
			this.wp1.TabIndex = 0;
			this.wp1.Text = "WorkPiece 1";
			// 
			// wp2
			// 
			this.wp2.Location = new System.Drawing.Point(152, 24);
			this.wp2.Name = "wp2";
			this.wp2.Size = new System.Drawing.Size(88, 24);
			this.wp2.TabIndex = 0;
			this.wp2.Text = "WorkPiece 2";
			// 
			// wp3
			// 
			this.wp3.Location = new System.Drawing.Point(248, 24);
			this.wp3.Name = "wp3";
			this.wp3.Size = new System.Drawing.Size(88, 24);
			this.wp3.TabIndex = 0;
			this.wp3.Text = "WorkPiece 3";
			// 
			// addWPButton
			// 
			this.addWPButton.Location = new System.Drawing.Point(360, 24);
			this.addWPButton.Name = "addWPButton";
			this.addWPButton.Size = new System.Drawing.Size(136, 24);
			this.addWPButton.TabIndex = 1;
			this.addWPButton.Text = "Add WorkPiece";
			this.addWPButton.Click += new System.EventHandler(this.AddWPButtonClick);
			// 
			// Faceplate
			// 
			this.Bounds = new NxtControl.Drawing.RectF(((float)(0)), ((float)(0)), ((float)(528)), ((float)(72)));
			this.FormBorderStyle = NxtControl.GuiFramework.FormBorderStyle.FixedSingle;
			this.Name = "Faceplate";
			this.Shapes.AddRange(new System.ComponentModel.IComponent[] {
									this.wp1,
									this.wp2,
									this.wp3,
									this.addWPButton});
			this.Size = new System.Drawing.Size(528, 72);
		}
		private NxtControl.GuiFramework.Button addWPButton;
		private NxtControl.GuiFramework.CheckBox wp3;
		private NxtControl.GuiFramework.CheckBox wp2;
		private NxtControl.GuiFramework.CheckBox wp1;
		#endregion
	}
}
