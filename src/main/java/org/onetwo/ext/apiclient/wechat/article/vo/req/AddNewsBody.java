package org.onetwo.ext.apiclient.wechat.article.vo.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author LeeKITMAN
 */
@Data
public class AddNewsBody {

    @NotNull
    private List<AddNewsBodyItem> articles;

}
