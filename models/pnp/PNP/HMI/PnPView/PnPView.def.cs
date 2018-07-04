/*
 * Created by nxtSTUDIO.
 * User: XPMUser
 * Date: 2/8/2012
 * Time: 11:04 AM
 * 
 * To change this template use Tools | Options | Coding | Edit Standard Headers.
 */
using System;
using NxtControl.GuiFramework;
using NxtControl.Services;

#region Definitions;
#region PnPView_HMI;

namespace NxtStudio.Symbols.PnPView
{
  partial class HMI
  {

    private NxtStudio.Faceplates.PnPView.Faceplate Faceplate
    {
      get
      { 
        if (IsOpenFaceplateSecure() == false)
          return null;

        NxtStudio.Faceplates.PnPView.Faceplate faceplate = null;
        
        IHMIManagementService hmiManagementService = (IHMIManagementService)ServiceProvider.GetService(typeof(IHMIManagementService));
        if (hmiManagementService != null)
          faceplate = (NxtStudio.Faceplates.PnPView.Faceplate)hmiManagementService.GetRegisteredHMIFaceplate(MapPath, typeof(NxtStudio.Faceplates.PnPView.Faceplate));
        
        if (faceplate == null)
        {
          faceplate = new NxtStudio.Faceplates.PnPView.Faceplate();

          faceplate.SetConnectionInfo(this.TagName, this.SymbolPath, this.ChannelId, GetType());

          if (hmiManagementService != null)
            hmiManagementService.RegisterHMIFaceplate(faceplate);
        }
        return faceplate;
      }
    }
     
    protected override void DoOpenFaceplate(OpenFaceplate openFaceplate)
    {
      NxtControl.GuiFramework.HMIFaceplate hmiFaceplate = null;

      if ("Faceplate" == (string)openFaceplate.FaceplateType)
        hmiFaceplate = Faceplate;

      if (hmiFaceplate != null)
      {
        if (hmiFaceplate.Initialized == true)
          hmiFaceplate.Activate();
        else
        {
          OnInitializeFaceplate(hmiFaceplate);
          hmiFaceplate.Show(this);
        }
      }
    }

    public override void DoOpenFaceplate(string openFaceplate)
    {
      NxtControl.GuiFramework.HMIFaceplate hmiFaceplate = null;

      if ("Faceplate" == openFaceplate)
        hmiFaceplate = Faceplate;

      if (hmiFaceplate != null)
      {
        if (hmiFaceplate.Initialized == true)
          hmiFaceplate.Activate();
        else
        {
          OnInitializeFaceplate(hmiFaceplate);
          hmiFaceplate.Show(this);
        }
      }
    }

  }
}
#endregion PnPView_HMI;

#endregion Definitions;
