<%@tag pageEncoding="UTF-8"%>
<script type="text/html" id="blade-template"> 
    <section class="fxs-blade-placeholder fxs-blade-paired-smallmaster fxs-blade fxs-stacklayout-child fxs-bladesize-small" data-bind="css:{'fxs-blade-maximized fxs-blade-maximized-expand':state()=='max','fxs-blade-minimized':state()=='min'},attr:{'data-load-url':url}"> 
        <header class="fxs-blade-header fxs-header fxs-blade-loaded fxs-bladestyle-hub fxs-blade-color-trim"> 
            <div class="fxs-blade-title-content"> 
                <div class="fxs-blade-title-text-container"> 
                    <div class="fxs-blade-title"> 
                        <div class="fxs-blade-hgroup"> 
                            <h2 class="fxs-blade-title-titleText msportalfx-tooltip-overflow"> 
                                <span data-bind="html:title"/>  
                                <!--ko if: subtitle()!=""-->  
                                <small class="blade-subheader" data-bind="html:subtitle"/>  
                                <!--/ko--> 
                            </h2> 
                        </div>  
                        <div class="fxs-blade-actions" role="menubar"> 
                            <button title="刷新" class="fxs-blade-refresh" role="menuitem" aria-label="刷新" type="button" data-bind="html:icons.refresh"/>  
                            <button title="关闭" class="fxs-blade-close" role="menuitem" aria-label="关闭" type="button" data-bind="html:icons.close"/>  
                            <!-- ko foreach: bladeCommands -->  
                            <button type="button" role="menuitem" data-bind="attr:{class:css,title:title},html:icon,click:click"/>  
                            <!-- /ko --> 
                        </div> 
                    </div> 
                </div>  
                <div class="fxs-blade-commandBarContainer"> 
                    <div class="fxs-commandBar-target fxs-commandBar"> 
                        <ul class="fxs-commandBar-itemList" role="menubar"> 
                            <!-- ko foreach: actionCommands -->  
                            <li class="fxs-commandBar-item fxs-br-default fxs-has-hover" tabindex="0" data-bind="attr:{title:text},click:click"> 
                                <div class="fxs-commandBar-item-buttoncontainer"> 
                                    <div class="fxs-commandBar-item-icon" data-bind="html:icon"/>  
                                    <div class="fxs-commandBar-item-text msportalfx-tooltip-overflow" data-bind="text:text"/> 
                                </div> 
                            </li>  
                            <!-- /ko --> 
                        </ul> 
                    </div> 
                </div> 
            </div> 
        </header>  
        <div class="fxs-blade-content-container"> 
            <div data-bind="css:{'fxs-blade-maximized':state()=='max'}" class="fxs-blade-content-container-default fxs-bladecontent fxs-mode-locked fxs-blade-locked fxs-bladestyle-default fxs-blade-color-locked fxs-bladesize-larger"> 
                <div class="fxs-blade-content-wrapper"> 
                    <div class="fxs-blade-summary-container fxs-has-border"/>  
                    <div class="fxs-blade-content fxs-pannable" data-bind="allowBindings:false"> 
                        <div class="spinner"> 
                            <div class="rect1"/>  
                            <div class="rect2"/>  
                            <div class="rect3"/>  
                            <div class="rect4"/>  
                            <div class="rect5"/> 
                        </div> 
                    </div> 
                </div> 
            </div> 
        </div> 
    </section> 
</script>