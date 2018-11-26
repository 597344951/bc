(function ($) {
    settings = {
        animation: 1,
        animationType: "in",
        backwards: false,
        easing: "easeOutQuint",
        speed: 1000,
        sequenceDelay: 50,
        startDelay: 0,
        offsetX: 100,
        offsetY: 50,
        restoreHTML: true
    };

    function setting() {
        animation: 1;
        animationType: "in";
        backwards: false;
        easing: "easeOutQuint";
        speed: 1000;
        sequenceDelay: 50;
        startDelay: 0;
        offsetX: 100;
        offsetY: 50;
        restoreHTML: true;
    }
    
    let s = new setting();


    jQuery(document).ready(function () {
        for (let i = 1; i <= 15; i++) {
            settings.animation = i;
            settings.startDelay = (i - 1) * 1500;
            jQuery.cjTextFx(settings);
            jQuery.cjTextFx.animate("#font" + i);
        }

        
    });
})(jQuery);