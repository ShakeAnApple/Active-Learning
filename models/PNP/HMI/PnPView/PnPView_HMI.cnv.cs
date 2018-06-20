/*
 * Created by nxtSTUDIO.
 * User: XPMUser
 * Date: 2/8/2012
 * Time: 11:04 AM
 * 
 * To change this template use Tools | Options | Coding | Edit Standard Headers.
 */

using System;
using System.Drawing;
using NxtStudio.Symbols;
using NxtControl.GuiFramework;
using System.Windows.Forms;

namespace NxtStudio.Symbols.PnPView
{
	/// <summary>
	/// Description of HMI.
	/// </summary>
	public partial class HMI : NxtControl.GuiFramework.HMISymbol
	{
	  ushort BASE_X;
    ushort BASE_Y;
    ushort CYL1_X;
    ushort CYL1_Y;
    ushort CYL2_X;
    ushort CYL2_Y;
    ushort VCYL_X;
    ushort VCYL_Y;
    ushort WP1_X;
    ushort WP1_Y;
    ushort WP2_X;
    ushort WP2_Y;
    ushort WP3_X;
    ushort WP3_Y;
    bool VACUUM_ON;
		public HMI()
		{
			//
			// The InitializeComponent() call is required for Windows Forms designer support.
			//
			InitializeComponent();
			this.REQ_Fired += new EventHandler<REQEventArgs>(onReq);
			
			//
			// TODO: Add constructor code after the InitializeComponent() call.
			//
		}

		
		void onReq(object sender, REQEventArgs ea)
    {  
		  //MessageBox.Show("OnREQ", "Req Yayy");
		  		    
		  ea.Get_BASE_X(ref BASE_X);
		  ea.Get_BASE_Y(ref BASE_Y);
		  ea.Get_CYL1_X(ref CYL1_X);
		  ea.Get_CYL1_Y(ref CYL1_Y);
		  ea.Get_CYL2_X(ref CYL2_X);
		  ea.Get_CYL2_Y(ref CYL2_Y);
		  ea.Get_VCYL_X(ref VCYL_X);
		  ea.Get_VCYL_Y(ref VCYL_Y);
		  ea.Get_WP1_X(ref WP1_X);
		  ea.Get_WP1_Y(ref WP1_Y);
		  ea.Get_WP2_X(ref WP2_X);
		  ea.Get_WP2_Y(ref WP2_Y);
		  ea.Get_WP3_X(ref WP3_X);
		  ea.Get_WP3_Y(ref WP3_Y);
		  ea.Get_VACUUM_ON(ref VACUUM_ON);
		  
		  pnpLC.X = CYL1_X;
		  pnpRC.X = CYL2_X;
		  pnpVC.X = VCYL_X;
		  pnpVC.Y = VCYL_Y;
		  vac.X = VCYL_X;
		  vac.Y = VCYL_Y;
		  wp1.X = WP1_X;
		  wp1.Y = WP1_Y;
		  wp2.X = WP2_X;
		  wp2.Y = WP2_Y;
		  wp3.X = WP3_X;
		  wp3.Y = WP3_Y;
		  if(VACUUM_ON)
		    vac.Visible = true;
		  else
		    vac.Visible = false;
    }
	}
}
