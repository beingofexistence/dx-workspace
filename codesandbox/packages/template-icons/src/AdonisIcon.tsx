import React from 'react';
import { SVGIcon, ISVGIconProps } from './SVGIcon';

export const AdonisIcon: React.FC<ISVGIconProps> = ({ ...props }) => (
  <SVGIcon {...props} viewBox="0 0 33 33">
    <path
      fillRule="evenodd"
      clipRule="evenodd"
      d="M0 16.3331C0 29.506 3.16017 32.6662 16.3331 32.6662C29.506 32.6662 32.6662 29.506 32.6662 16.3331C32.6662 3.16017 29.506 0 16.3331 0C3.16017 0 0 3.16017 0 16.3331ZM6.58646 19.7261L11.7093 8.08338C12.5742 6.12075 14.2374 5.05627 16.3331 5.05627C18.4288 5.05627 20.092 6.12075 20.9569 8.08338L26.0797 19.7261C26.3126 20.2916 26.5122 21.0235 26.5122 21.6555C26.5122 24.5495 24.483 26.5787 21.589 26.5787C20.6032 26.5787 19.8203 26.3271 19.0278 26.0725C18.2158 25.8116 17.3937 25.5475 16.3331 25.5475C15.2847 25.5475 14.4426 25.814 13.6145 26.076C12.8136 26.3294 12.0258 26.5787 11.0772 26.5787C8.18318 26.5787 6.15402 24.5495 6.15402 21.6555C6.15402 21.0235 6.35361 20.2916 6.58646 19.7261ZM16.3331 10.1125L11.2768 21.5557C12.7737 20.8571 14.5035 20.5245 16.3331 20.5245C18.0961 20.5245 19.8924 20.8571 21.3228 21.5557L16.3331 10.1125Z"
      fill="white"
    />
  </SVGIcon>
);