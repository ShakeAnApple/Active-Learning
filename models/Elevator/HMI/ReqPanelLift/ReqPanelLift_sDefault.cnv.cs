/*
 * Created by nxtSTUDIO.
 * User: Andrei
 * Date: 6/3/2016
 * Time: 12:05 PM
 * 
 */

using System;
using System.Drawing;
using NxtControl.GuiFramework;

namespace HMI.Main.Symbols.ReqPanelLift
{
	/// <summary>
	/// Description of sDefault.
	/// </summary>
	public partial class sDefault : NxtControl.GuiFramework.HMISymbol
	{
		public sDefault()
		{
			//
			// The InitializeComponent() call is required for Windows Forms designer support.
			//
			InitializeComponent();
      this.REQ0_Fired += REQ0_Fired_EventHandler;
      this.REQ1_Fired += REQ1_Fired_EventHandler;
      this.REQ2_Fired += REQ2_Fired_EventHandler;
      led0.Visible = false;
      led1.Visible = false;
      led2.Visible = false;
		}
				
		void Button0Click(object sender, EventArgs e)
		{
		  led0.Visible = true;
		  this.FireEvent_CHG(true,false,true,true,true,true);
		}
		
		void Button1Click(object sender, EventArgs e)
		{
		  led1.Visible = true;
		  this.FireEvent_CHG(true,true,true,false,true,true);
		}
		
		void Button2Click(object sender, EventArgs e)
		{
		  led2.Visible = true;
		  this.FireEvent_CHG(true,true,true,true,true,false);
		}
		
		void REQ0_Fired_EventHandler(object sender, HMI.Main.Symbols.ReqPanelLift.REQ0EventArgs ea)
		{		  
		  bool buttonOn = !ea.Button0Reset.Value;
		  led0.Visible = buttonOn;
	    this.FireEvent_CHG(true,false,true,true,true,true);
	    NxtControl.Services.LoggingService.Info("iN rEQ0");
		}
		
		void REQ1_Fired_EventHandler(object sender, HMI.Main.Symbols.ReqPanelLift.REQ1EventArgs ea)
		{
			//if ((bool)ea.Button1Reset)
	   // {
	      bool buttonOn = !ea.Button1Reset.Value;
	      led1.Visible = false;
	      this.FireEvent_CHG(true,true,true,false,true,true);
	      NxtControl.Services.LoggingService.Info("iN rEQ1");
	   // }
		}
		
		void REQ2_Fired_EventHandler(object sender, HMI.Main.Symbols.ReqPanelLift.REQ2EventArgs ea)
		{
			  bool buttonOn = !ea.Button2Reset.Value;
	      led2.Visible = false;
	      this.FireEvent_CHG(true,true,true,true,true,false);
	      NxtControl.Services.LoggingService.Info("iN rEQ2");
		}
	}
}
