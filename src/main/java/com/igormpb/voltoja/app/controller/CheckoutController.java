    package com.igormpb.voltoja.app.controller;

    import com.igormpb.voltoja.app.service.CheckoutService;
    import com.igormpb.voltoja.domain.errors.HandleErros;
    import jakarta.servlet.http.HttpServletRequest;
    import com.igormpb.voltoja.domain.request.PostPaymentCardWithCheckoutRequest;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/checkout")
    public class CheckoutController {
        public record UrlResponse(String url) {}

        @Autowired
        CheckoutService checkoutService;

        @PostMapping("/{id}")
        public ResponseEntity Checkout(@PathVariable(value = "id") String id, @RequestBody List<PostPaymentCardWithCheckoutRequest> request, HttpServletRequest httpRequest) {
            try{
                String accountId = (String) httpRequest.getAttribute("account_id");
                var url = checkoutService.PaymentCardCheckoutURL(id,request,accountId);

                return ResponseEntity.ok(new UrlResponse(url));
            }catch (HandleErros e){
                return ResponseEntity.status(e.GetResponseError().status()).body(e.GetResponseError());
            }
        }

    }
