/*
 * Created by nxtSTUDIO.
 * User: XPMUser
 * Date: 2/8/2012
 * Time: 7:03 PM
 * 
 * To change this template use Tools | Options | Coding | Edit Standard Headers.
 */

using System;
using System.Drawing;
using NxtControl.GuiFramework;
using System.Windows.Forms;

namespace NxtStudio.Faceplates.PnPView
{
	/// <summary>
	/// Description of Faceplate.
	/// </summary>
	public partial class Faceplate : NxtControl.GuiFramework.HMIFaceplate
	{
	  bool wp1Selected;
	  bool wp2Selected;
	  bool wp3Selected;

		public Faceplate()
		{
			//
			// The InitializeComponent() call is required for Windows Forms designer support.
			//
			InitializeComponent();
			
			//
			// TODO: Add constructor code after the InitializeComponent() call.
			//
		}
		
		void AddWPButtonClick(object sender, EventArgs e)
		{
		  wp1Selected = wp1.Checked;
			wp2Selected = wp2.Checked;
			wp3Selected = wp3.Checked;
			FireEvent_CNF(wp1Selected, wp2Selected, wp3Selected);
			//MessageBox.Show("Yea", "Yayy");
		}
	}
}
