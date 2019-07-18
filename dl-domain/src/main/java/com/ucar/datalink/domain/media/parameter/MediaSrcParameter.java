package com.ucar.datalink.domain.media.parameter;

import com.ucar.datalink.domain.Parameter;
import com.ucar.datalink.domain.media.MediaSourceType;

/**
 * Created by user on 2017/3/3.
 */
public abstract class MediaSrcParameter extends Parameter {
    private MediaSourceType mediaSourceType;
    private String namespace;
    //为区分oracle与mysql添加
    private String namespace_oracle;
    public MediaSourceType getMediaSourceType() {
        return mediaSourceType;
    }

    public void setMediaSourceType(MediaSourceType mediaSourceType) {
        this.mediaSourceType = mediaSourceType;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getNamespace_oracle() {
        return namespace_oracle;
    }

    public void setNamespace_oracle(String namespace_oracle) {
        this.namespace_oracle = namespace_oracle;
    }
}
